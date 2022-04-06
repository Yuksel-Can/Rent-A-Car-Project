package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalAddModel;
import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalUpdateModel;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForCorporateRentAdd;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForCorporateRentUpdate;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForIndividualRentAdd;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForIndividualRentUpdate;
import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.dtos.gets.payment.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.lists.payment.PaymentListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateRentalCarRequest;
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
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;

    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, @Lazy CarService carService, @Lazy RentalCarService rentalCarService, @Lazy OrderedAdditionalService orderedAdditionalService,
                          @Lazy InvoiceService invoiceService, @Lazy PostService postService, @Lazy IndividualCustomerService individualCustomerService,@Lazy CorporateCustomerService corporateCustomerService) {
        this.paymentDao = paymentDao;
        this.carService = carService;
        this.modelMapperService = modelMapperService;
        this.rentalCarService = rentalCarService;
        this.orderedAdditionalService = orderedAdditionalService;
        this.invoiceService = invoiceService;
        this.postService = postService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
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
    public Result makePaymentForIndividualRentAdd(MakePaymentForIndividualRentAdd makePayment) throws BusinessException {

        checkAllValidationsForIndividualAdd(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

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
    public Result makePaymentForCorporateRentAdd(MakePaymentForCorporateRentAdd makePayment) throws BusinessException {

        checkAllValidationsForCorporateAdd(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

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
    public Result makePaymentForIndividualRentUpdate(MakePaymentForIndividualRentUpdate makePaymentForIndividualRentUpdate) throws BusinessException {
       return null;
    }

    @Override
    public Result makePaymentForCorporateRentUpdate(MakePaymentForCorporateRentUpdate makePaymentForCorporateRentUpdate) throws BusinessException {
        return null;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result makePaymentForOrderedAdditionalAdd(OrderedAdditionalAddModel orderedAdditionalAddModel) throws BusinessException {

        checkAllValidationsForOrderedAdditionalAdd(orderedAdditionalAddModel.getRentalCarId(), orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList());

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
    @Transactional(rollbackFor = BusinessException.class)
    public Result makePaymentForOrderedAdditionalUpdate(OrderedAdditionalUpdateModel orderedAdditionalUpdateModel) throws BusinessException {

        checkAllValidationsForOrderedAdditionalUpdate(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());

        double totalPrice = calculatePriceDifferenceWithPreviousOrderedAdditional(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());
        //todo: projedeki tek if
        if(totalPrice > 0){
            this.postService.payment(orderedAdditionalUpdateModel.getCreatePaymentRequest().getCardNumber(), orderedAdditionalUpdateModel.getCreatePaymentRequest().getCardOwner(),
                    orderedAdditionalUpdateModel.getCreatePaymentRequest().getCardCvv(), orderedAdditionalUpdateModel.getCreatePaymentRequest().getCardExpirationDate(), orderedAdditionalUpdateModel.getCreatePaymentRequest().getTotalPrice());

            orderedAdditionalUpdateModel.getCreatePaymentRequest().setTotalPrice(totalPrice);
            orderedAdditionalUpdateModel.getCreatePaymentRequest().setRentalCarId(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest().getRentalCarId());
            Payment payment = this.modelMapperService.forRequest().map(orderedAdditionalUpdateModel.getCreatePaymentRequest(), Payment.class);
            payment.setPaymentId(0);

            int paymentId = this.paymentDao.save(payment).getPaymentId();

            this.orderedAdditionalService.update(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());

            this.invoiceService.createAndAddInvoice(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest().getRentalCarId(), paymentId);

            return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
        }

        this.orderedAdditionalService.update(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());

        return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
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

    private void checkAllValidationsForIndividualAdd(CreateRentalCarRequest createRentalCarRequest, List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList) throws BusinessException {
        this.rentalCarService.checkAllValidationsForIndividualRentAdd(createRentalCarRequest);
        this.orderedAdditionalService.checkAllValidationForAddOrderedAdditional(createOrderedAdditionalRequestList);
    }

    private void checkAllValidationsForCorporateAdd(CreateRentalCarRequest createRentalCarRequest, List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList) throws BusinessException {
        this.rentalCarService.checkAllValidationsForCorporateRentAdd(createRentalCarRequest);
        this.orderedAdditionalService.checkAllValidationForAddOrderedAdditional(createOrderedAdditionalRequestList);
    }
    private void checkAllValidationsForIndividualUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {
        this.rentalCarService.checkAllValidationsForIndividualRentUpdate(updateRentalCarRequest);
    }

    private void checkAllValidationsForCorporateUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {
        this.rentalCarService.checkAllValidationsForCorporateRentUpdate(updateRentalCarRequest);
    }

    private void checkAllValidationsForOrderedAdditionalAdd(int rentalCarId, List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList) throws BusinessException {
        this.rentalCarService.checkIsExistsByRentalCarId(rentalCarId);
        this.orderedAdditionalService.checkAllValidationForAddOrderedAdditional(createOrderedAdditionalRequestList);
    }

    private void checkAllValidationsForOrderedAdditionalUpdate(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {
        this.orderedAdditionalService.checkIsExistsByOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());
        this.rentalCarService.checkIsExistsByRentalCarId(updateOrderedAdditionalRequest.getRentalCarId());
        this.orderedAdditionalService.checkAllValidationForAddOrderedAdditional(updateOrderedAdditionalRequest.getAdditionalId(), updateOrderedAdditionalRequest.getOrderedAdditionalQuantity());
        this.orderedAdditionalService.checkIsOnlyOneOrderedAdditionalByAdditionalIdAndRentalCarIdForUpdate(updateOrderedAdditionalRequest.getAdditionalId(), updateOrderedAdditionalRequest.getRentalCarId());
    }

    //todo:bu burada doğrumu

    private double calculateTotalPrice(CreateRentalCarRequest rentalCarRequest, List<CreateOrderedAdditionalRequest> orderedAdditionalRequestList) throws BusinessException {

        int totalDays = this.rentalCarService.getTotalDaysForRental(rentalCarRequest.getStartDate(), rentalCarRequest.getFinishDate());
        double priceOfRental = this.rentalCarService.calculateAndReturnRentPrice(rentalCarRequest.getStartDate(), rentalCarRequest.getFinishDate(),
                this.carService.getDailyPriceByCarId(rentalCarRequest.getCarId()), rentalCarRequest.getRentedCityCityId(), rentalCarRequest.getDeliveredCityId());
        double priceOfAdditionals = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(orderedAdditionalRequestList,totalDays);

        return priceOfRental + priceOfAdditionals;
    }

    private double calculatePriceDifferenceWithPreviousOrderedAdditional(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {
        OrderedAdditional orderedAdditional = this.orderedAdditionalService.getById(updateOrderedAdditionalRequest.getOrderedAdditionalId());

        double beforePrice = this.orderedAdditionalService.getPriceCalculatorForAdditional(orderedAdditional.getAdditional().getAdditionalId(),
                orderedAdditional.getOrderedAdditionalQuantity(), this.rentalCarService.getTotalDaysForRental(orderedAdditional.getRentalCar().getRentalCarId()));

        double afterPrice = this.orderedAdditionalService.getPriceCalculatorForAdditional(updateOrderedAdditionalRequest.getAdditionalId(),
                updateOrderedAdditionalRequest.getOrderedAdditionalQuantity(), this.rentalCarService.getTotalDaysForRental(updateOrderedAdditionalRequest.getRentalCarId()));

        if(afterPrice>beforePrice){
            return afterPrice - beforePrice;
        }
        return 0;
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
