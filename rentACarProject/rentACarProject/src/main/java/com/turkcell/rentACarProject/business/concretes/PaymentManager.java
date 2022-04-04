package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForCorporateCustomer;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForIndividualCustomer;
import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.dtos.gets.payment.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.lists.payment.PaymentListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.core.postServices.PostService;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACarProject.entities.concretes.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {

    private PaymentDao paymentDao;
    private ModelMapperService modelMapperService;
    private CarService carService;
    private RentalCarService rentalCarService;
    private OrderedAdditionalService orderedAdditionalService;
    private InvoiceService invoiceService;
    private PostService postService;

    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, @Lazy CarService carService,
                          @Lazy RentalCarService rentalCarService, @Lazy OrderedAdditionalService orderedAdditionalService,
                          @Lazy InvoiceService invoiceService, @Lazy PostService postService) {
        this.paymentDao = paymentDao;
        this.carService = carService;
        this.modelMapperService = modelMapperService;
        this.rentalCarService = rentalCarService;
        this.orderedAdditionalService = orderedAdditionalService;
        this.invoiceService = invoiceService;
        this.postService = postService;
    }


    @Override
    public DataResult<List<PaymentListDto>> getAll() {

        List<Payment> payments = this.paymentDao.findAll();

        List<PaymentListDto> result = payments.stream().map(payment -> this.modelMapperService.forDto()
                .map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(result, "payment lister");
    }

    @Override
    public Result makePaymentForIndividualCustomer(MakePaymentForIndividualCustomer makePayment) throws BusinessException {

        checkAllValidations(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

        double totalPrice = calculateTotalPrice(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

        this.postService.payment(makePayment.getCreatePaymentRequest().getCardNumber(), makePayment.getCreatePaymentRequest().getCardOwner(),
                                    makePayment.getCreatePaymentRequest().getCardCvv(), makePayment.getCreatePaymentRequest().getCardExpirationDate(), totalPrice);




        Payment payment = this.modelMapperService.forRequest().map(makePayment.getCreatePaymentRequest(), Payment.class);

        this.paymentDao.save(payment);


        //validasyon yap
        //tutar hesapla
        //ödeme yap
        //ödeme kaydet
        //rent kaydet
        //ordered kaydet
        //fatura oluştur






        this.paymentDao.save(payment);

        return new SuccessResult("Payment added");
    }

    private double calculateTotalPrice(CreateRentalCarRequest rentalCarRequest, List<CreateOrderedAdditionalRequest> orderedAdditionalRequestList) throws BusinessException {

        int totalDays = this.rentalCarService.getTotalDaysForRental(rentalCarRequest.getStartDate(),rentalCarRequest.getFinishDate());
        double priceOfDays = this.rentalCarService.calculateRentalCarTotalDayPrice(rentalCarRequest.getStartDate(), rentalCarRequest.getFinishDate(), this.carService.getDailyPriceByCarId(rentalCarRequest.getCarId()));
        double priceOfDiffCity = this.rentalCarService.calculateCarDeliveredToTheSamCity(rentalCarRequest.getRentedCityCityId(), rentalCarRequest.getRentedCityCityId());
        double priceOfAdditionals = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(orderedAdditionalRequestList,totalDays);

        return priceOfDays + priceOfDiffCity + priceOfAdditionals;
    }

    @Override
    public Result makePaymentForIndividualCustomer(MakePaymentForCorporateCustomer makePaymentForCorporateCustomer) {

        Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

        this.paymentDao.save(payment);

        return new SuccessResult("Payment added");
    }

    @Override
    public DataResult<GetPaymentDto> getById(int paymentId) throws BusinessException {

        checkIfExistsByPaymentId(paymentId);

        Payment payment = this.paymentDao.getById(paymentId);

        GetPaymentDto result = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);

        return new SuccessDataResult<>(result, "Payment getted by id, paymentId: " + paymentId );
    }

    @Override
    public DataResult<List<PaymentListDto>> getAllPaymentByRentalCar_RentalCarId(int rentalCarId) throws BusinessException {

        checkIfExistsByRentalCar_RentalCarId(rentalCarId);

        List<Payment> payments = this.paymentDao.getAllByRentalCar_RentalCarId(rentalCarId);

        List<PaymentListDto> result = payments.stream().map(payment -> this.modelMapperService.forDto()
                .map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Payments listed by rental car id, rentalCarId: " + rentalCarId);
    }


    private void checkAllValidations(CreateRentalCarRequest createRentalCarRequest, List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList) throws BusinessException {

        this.rentalCarService.checkAllValidationsForAddRent(createRentalCarRequest);
        this.orderedAdditionalService.checkAllValidationForAddOrderedAdditional(createOrderedAdditionalRequestList);
    }

    private void checkIfExistsByPaymentId(int paymentId) throws BusinessException {
        if(!this.paymentDao.existsByPaymentId(paymentId)){
            throw new BusinessException("Payment not found, paymentId: " + paymentId);
        }
    }

    private void checkIfExistsByRentalCar_RentalCarId(int rentalCarId) throws BusinessException {
        if(!this.paymentDao.existsByRentalCar_RentalCarId(rentalCarId)){
            throw new BusinessException("Payment not found by rental car id, rentalCarId: " + rentalCarId);
        }
    }
}
