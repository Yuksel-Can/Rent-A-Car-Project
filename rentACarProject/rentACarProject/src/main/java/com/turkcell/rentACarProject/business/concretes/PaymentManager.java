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
import com.turkcell.rentACarProject.business.adapters.posAdapters.PosService;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;
import com.turkcell.rentACarProject.entities.concretes.Payment;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;
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
    private final PosService posService;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;

    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, @Lazy CarService carService, @Lazy RentalCarService rentalCarService, @Lazy OrderedAdditionalService orderedAdditionalService,
                          @Lazy InvoiceService invoiceService, @Lazy PosService posService, @Lazy IndividualCustomerService individualCustomerService, @Lazy CorporateCustomerService corporateCustomerService) {
        this.paymentDao = paymentDao;
        this.carService = carService;
        this.modelMapperService = modelMapperService;
        this.rentalCarService = rentalCarService;
        this.orderedAdditionalService = orderedAdditionalService;
        this.invoiceService = invoiceService;
        this.posService = posService;
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

        this.posService.payment(makePayment.getCreatePaymentRequest().getCardNumber(), makePayment.getCreatePaymentRequest().getCardOwner(),
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

        this.posService.payment(makePayment.getCreatePaymentRequest().getCardNumber(), makePayment.getCreatePaymentRequest().getCardOwner(),
                makePayment.getCreatePaymentRequest().getCardCvv(), makePayment.getCreatePaymentRequest().getCardExpirationDate(), makePayment.getCreatePaymentRequest().getTotalPrice());

        int rentalCarId = this.rentalCarService.addForCorporateCustomer(makePayment.getCreateRentalCarRequest());

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

        checkAllValidationsForIndividualUpdate(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest());

        double totalPrice = calculatePriceDifferenceWithPreviousRentalCar(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest());
        //todo: projedeki tek ikinci
        if(totalPrice > 0){
            this.posService.payment(makePaymentForIndividualRentUpdate.getCreatePaymentRequest().getCardNumber(), makePaymentForIndividualRentUpdate.getCreatePaymentRequest().getCardOwner(),
                    makePaymentForIndividualRentUpdate.getCreatePaymentRequest().getCardCvv(), makePaymentForIndividualRentUpdate.getCreatePaymentRequest().getCardExpirationDate(), totalPrice);

            makePaymentForIndividualRentUpdate.getCreatePaymentRequest().setRentalCarId(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest().getRentalCarId());
            makePaymentForIndividualRentUpdate.getCreatePaymentRequest().setTotalPrice(totalPrice);
            Payment payment = this.modelMapperService.forRequest().map(makePaymentForIndividualRentUpdate.getCreatePaymentRequest(), Payment.class);
            payment.setPaymentId(0);

            this.rentalCarService.updateForIndividualCustomer(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest());

            int paymentId = this.paymentDao.save(payment).getPaymentId();

            this.invoiceService.createAndAddInvoice(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest().getRentalCarId(), paymentId);

            return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
        }

        this.rentalCarService.updateForIndividualCustomer(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest());

        return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
    }

    @Override
    public Result makePaymentForCorporateRentUpdate(MakePaymentForCorporateRentUpdate makePaymentForCorporateRentUpdate) throws BusinessException {

        checkAllValidationsForCorporateUpdate(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest());

        double totalPrice = calculatePriceDifferenceWithPreviousRentalCar(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest());

        if(totalPrice > 0){
            this.posService.payment(makePaymentForCorporateRentUpdate.getCreatePaymentRequest().getCardNumber(), makePaymentForCorporateRentUpdate.getCreatePaymentRequest().getCardOwner(),
                    makePaymentForCorporateRentUpdate.getCreatePaymentRequest().getCardCvv(), makePaymentForCorporateRentUpdate.getCreatePaymentRequest().getCardExpirationDate(), totalPrice);

            makePaymentForCorporateRentUpdate.getCreatePaymentRequest().setRentalCarId(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest().getRentalCarId());
            makePaymentForCorporateRentUpdate.getCreatePaymentRequest().setTotalPrice(totalPrice);
            Payment payment = this.modelMapperService.forRequest().map(makePaymentForCorporateRentUpdate.getCreatePaymentRequest(), Payment.class);
            payment.setPaymentId(0);

            this.rentalCarService.updateForCorporateCustomer(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest());

            int paymentId = this.paymentDao.save(payment).getPaymentId();

            this.invoiceService.createAndAddInvoice(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest().getRentalCarId(), paymentId);

            return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
        }

        this.rentalCarService.updateForCorporateCustomer(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest());

        return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result makePaymentForOrderedAdditionalAdd(OrderedAdditionalAddModel orderedAdditionalAddModel) throws BusinessException {

        checkAllValidationsForOrderedAdditionalAdd(orderedAdditionalAddModel.getRentalCarId(), orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList());

        double totalPrice = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList(),
                                                                                    this.rentalCarService.getTotalDaysForRental(orderedAdditionalAddModel.getRentalCarId()));
        orderedAdditionalAddModel.getCreatePaymentRequest().setTotalPrice(totalPrice);
        orderedAdditionalAddModel.getCreatePaymentRequest().setRentalCarId(orderedAdditionalAddModel.getRentalCarId());

        this.posService.payment(orderedAdditionalAddModel.getCreatePaymentRequest().getCardNumber(), orderedAdditionalAddModel.getCreatePaymentRequest().getCardOwner(),
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

        if(totalPrice > 0){
            this.posService.payment(orderedAdditionalUpdateModel.getCreatePaymentRequest().getCardNumber(), orderedAdditionalUpdateModel.getCreatePaymentRequest().getCardOwner(),
                    orderedAdditionalUpdateModel.getCreatePaymentRequest().getCardCvv(), orderedAdditionalUpdateModel.getCreatePaymentRequest().getCardExpirationDate(), totalPrice);

            orderedAdditionalUpdateModel.getCreatePaymentRequest().setTotalPrice(totalPrice);
            orderedAdditionalUpdateModel.getCreatePaymentRequest().setRentalCarId(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest().getRentalCarId());
            Payment payment = this.modelMapperService.forRequest().map(orderedAdditionalUpdateModel.getCreatePaymentRequest(), Payment.class);
            payment.setPaymentId(0);

            this.orderedAdditionalService.update(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());

            int paymentId = this.paymentDao.save(payment).getPaymentId();

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

    private double calculatePriceDifferenceWithPreviousRentalCar(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {

        RentalCar beforeRentalCar = this.rentalCarService.getById(updateRentalCarRequest.getRentalCarId());

        double previousPrice = this.rentalCarService.calculateAndReturnRentPrice(beforeRentalCar.getStartDate(), beforeRentalCar.getFinishDate(), beforeRentalCar.getCar().getDailyPrice(),
                                                                            beforeRentalCar.getRentedCity().getCityId(), beforeRentalCar.getDeliveredCity().getCityId());
        double nextPrice = this.rentalCarService.calculateAndReturnRentPrice(updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate(),
                this.carService.getDailyPriceByCarId(updateRentalCarRequest.getCarId()), updateRentalCarRequest.getRentedCityId(), updateRentalCarRequest.getDeliveredCityId());

        if(nextPrice>previousPrice){
            return nextPrice-previousPrice;
        }
        return 0;
    }

    private double calculatePriceDifferenceWithPreviousOrderedAdditional(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {
        OrderedAdditional orderedAdditional = this.orderedAdditionalService.getById(updateOrderedAdditionalRequest.getOrderedAdditionalId());

        double previousPrice = this.orderedAdditionalService.getPriceCalculatorForAdditional(orderedAdditional.getAdditional().getAdditionalId(),
                orderedAdditional.getOrderedAdditionalQuantity(), this.rentalCarService.getTotalDaysForRental(orderedAdditional.getRentalCar().getRentalCarId()));

        double nextPrice = this.orderedAdditionalService.getPriceCalculatorForAdditional(updateOrderedAdditionalRequest.getAdditionalId(),
                updateOrderedAdditionalRequest.getOrderedAdditionalQuantity(), this.rentalCarService.getTotalDaysForRental(updateOrderedAdditionalRequest.getRentalCarId()));

        if(nextPrice>previousPrice){
            return nextPrice - previousPrice;
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
