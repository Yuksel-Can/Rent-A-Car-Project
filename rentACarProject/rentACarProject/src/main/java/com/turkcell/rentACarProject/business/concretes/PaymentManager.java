package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalAddModel;
import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalUpdateModel;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForCorporateCustomer;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForIndividualCustomer;
import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.dtos.gets.payment.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.lists.payment.PaymentListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.core.postServices.PostService;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;
import com.turkcell.rentACarProject.entities.concretes.Payment;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {

    private final PaymentDao paymentDao;
    private final ModelMapperService modelMapperService;
    private final CarService carService;
    private final RentalCarService rentalCarService;
    private final OrderedAdditionalService orderedAdditionalService;
    private final InvoiceService invoiceService;
    private final PostService postService;

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
        manuelIdSetter(payments, result);

        return new SuccessDataResult<>(result, "payment lister");
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result makePaymentForIndividualCustomer(MakePaymentForIndividualCustomer makePayment) throws BusinessException {

        checkAllValidationsForIndividual(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

        double totalPrice = calculateTotalPrice(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());
        makePayment.getCreatePaymentRequest().setTotalPrice(totalPrice);

        this.postService.payment(makePayment.getCreatePaymentRequest().getCardNumber(), makePayment.getCreatePaymentRequest().getCardOwner(),
                                    makePayment.getCreatePaymentRequest().getCardCvv(), makePayment.getCreatePaymentRequest().getCardExpirationDate(), makePayment.getCreatePaymentRequest().getTotalPrice());

        int rentalCarId = this.rentalCarService.addForIndividualCustomer(makePayment.getCreateRentalCarRequest());

        makePayment.getCreatePaymentRequest().setRentalCarId(rentalCarId);
        Payment payment = this.modelMapperService.forRequest().map(makePayment.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);
        int paymentId = this.paymentDao.save(payment).getPaymentId();

        saveOrderedAdditional(makePayment.getCreateOrderedAdditionalRequestList(), rentalCarId);

        this.invoiceService.createAndAddInvoice(rentalCarId, paymentId);

        return new SuccessResult("Payment, Car Rental, Additional Service adding and Invoice creation successful");
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result makePaymentForCorporateCustomer(MakePaymentForCorporateCustomer makePayment) throws BusinessException {

        checkAllValidationsForCorporate(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

        double totalPrice = calculateTotalPrice(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());
        makePayment.getCreatePaymentRequest().setTotalPrice(totalPrice);

        this.postService.payment(makePayment.getCreatePaymentRequest().getCardNumber(), makePayment.getCreatePaymentRequest().getCardOwner(),
                makePayment.getCreatePaymentRequest().getCardCvv(), makePayment.getCreatePaymentRequest().getCardExpirationDate(), makePayment.getCreatePaymentRequest().getTotalPrice());

        int rentalCarId = this.rentalCarService.addForIndividualCustomer(makePayment.getCreateRentalCarRequest());

        makePayment.getCreatePaymentRequest().setRentalCarId(rentalCarId);
        Payment payment = this.modelMapperService.forRequest().map(makePayment.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);
        int paymentId = this.paymentDao.save(payment).getPaymentId();

        saveOrderedAdditional(makePayment.getCreateOrderedAdditionalRequestList(), rentalCarId);

        this.invoiceService.createAndAddInvoice(rentalCarId, paymentId);

        return new SuccessResult("Payment, Car Rental, Additional Service adding and Invoice creation successful");
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result makePaymentForOrderedAdditionalAdd(OrderedAdditionalAddModel orderedAdditionalAddModel) throws BusinessException {

        this.rentalCarService.checkIsExistsByRentalCarId(orderedAdditionalAddModel.getRentalCarId());
        this.orderedAdditionalService.checkAllValidationForAddOrderedAdditional(orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList());

        double totalPrice = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList(),
                                                                                    this.rentalCarService.getTotalDaysForRental(orderedAdditionalAddModel.getRentalCarId()));
        orderedAdditionalAddModel.getCreatePaymentRequest().setTotalPrice(totalPrice);
        orderedAdditionalAddModel.getCreatePaymentRequest().setRentalCarId(orderedAdditionalAddModel.getRentalCarId());

        this.postService.payment(orderedAdditionalAddModel.getCreatePaymentRequest().getCardNumber(), orderedAdditionalAddModel.getCreatePaymentRequest().getCardOwner(),
                orderedAdditionalAddModel.getCreatePaymentRequest().getCardCvv(), orderedAdditionalAddModel.getCreatePaymentRequest().getCardExpirationDate(), orderedAdditionalAddModel.getCreatePaymentRequest().getTotalPrice());

        Payment payment = this.modelMapperService.forRequest().map(orderedAdditionalAddModel.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);
        int paymentId = this.paymentDao.save(payment).getPaymentId();

        saveOrderedAdditional(orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList(), orderedAdditionalAddModel.getRentalCarId());

        this.invoiceService.createAndAddInvoice(orderedAdditionalAddModel.getRentalCarId(), paymentId);

        return new SuccessResult("Payment, Additional Service adding and Invoice creation successful");

    }


    @Override
    public DataResult<GetPaymentDto> getById(int paymentId) throws BusinessException {

        checkIfExistsByPaymentId(paymentId);

        Payment payment = this.paymentDao.getById(paymentId);

        GetPaymentDto result = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);
        result.setRentalCarId(payment.getRentalCar().getRentalCarId());
        result.setInvoiceId(payment.getPaymentId());

        return new SuccessDataResult<>(result, "Payment getted by id, paymentId: " + paymentId );
    }

    @Override
    public DataResult<List<PaymentListDto>> getAllPaymentByRentalCar_RentalCarId(int rentalCarId) throws BusinessException {

        checkIfExistsByRentalCar_RentalCarId(rentalCarId);

        List<Payment> payments = this.paymentDao.getAllByRentalCar_RentalCarId(rentalCarId);

        List<PaymentListDto> result = payments.stream().map(payment -> this.modelMapperService.forDto()
                .map(payment, PaymentListDto.class)).collect(Collectors.toList());
        manuelIdSetter(payments, result);

        return new SuccessDataResult<>(result, "Payments listed by rental car id, rentalCarId: " + rentalCarId);
    }


    private void saveOrderedAdditional(List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList, int rentalCarId) throws BusinessException {
        for(CreateOrderedAdditionalRequest createOrderedAdditionalRequest : createOrderedAdditionalRequestList){
            createOrderedAdditionalRequest.setRentalCarId(rentalCarId);
            this.orderedAdditionalService.add(createOrderedAdditionalRequest);
        }
    }

    private void checkAllValidationsForIndividual(CreateRentalCarRequest createRentalCarRequest, List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList) throws BusinessException {
        this.rentalCarService.checkAllValidationsForAddIndividualRent(createRentalCarRequest);
        this.orderedAdditionalService.checkAllValidationForAddOrderedAdditional(createOrderedAdditionalRequestList);
    }

    private void checkAllValidationsForCorporate(CreateRentalCarRequest createRentalCarRequest, List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList) throws BusinessException {
        this.rentalCarService.checkAllValidationsForAddCorporateRent(createRentalCarRequest);
        this.orderedAdditionalService.checkAllValidationForAddOrderedAdditional(createOrderedAdditionalRequestList);
    }

    private double calculateTotalPrice(CreateRentalCarRequest rentalCarRequest, List<CreateOrderedAdditionalRequest> orderedAdditionalRequestList) throws BusinessException {

        int totalDays = this.rentalCarService.getTotalDaysForRental(rentalCarRequest.getStartDate(), rentalCarRequest.getFinishDate());
        double priceOfRental = this.rentalCarService.calculateAndReturnRentPrice(rentalCarRequest.getStartDate(), rentalCarRequest.getFinishDate(),
                this.carService.getDailyPriceByCarId(rentalCarRequest.getCarId()), rentalCarRequest.getRentedCityCityId(), rentalCarRequest.getDeliveredCityId());
        double priceOfAdditionals = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(orderedAdditionalRequestList,totalDays);

        return priceOfRental + priceOfAdditionals;
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

    private void manuelIdSetter(List<Payment> paymentList, List<PaymentListDto> paymentListDtoList){
        for(int i=0;i<paymentList.size();i++){
            paymentListDtoList.get(i).setRentalCarId(paymentList.get(i).getRentalCar().getRentalCarId());
        }
    }
}
