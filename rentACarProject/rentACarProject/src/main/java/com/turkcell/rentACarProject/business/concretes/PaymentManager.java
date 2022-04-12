package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalAddModel;
import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalUpdateModel;
import com.turkcell.rentACarProject.api.models.rentalCar.*;
import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.dtos.gets.payment.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.lists.payment.PaymentListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateDeliveryDateRequest;
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
import org.springframework.transaction.annotation.Propagation;
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
    private final CreditCardService creditCardService;

    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, @Lazy CarService carService, @Lazy RentalCarService rentalCarService, @Lazy OrderedAdditionalService orderedAdditionalService,
                          @Lazy InvoiceService invoiceService, @Lazy PosService posService, @Lazy IndividualCustomerService individualCustomerService, @Lazy CorporateCustomerService corporateCustomerService, CreditCardService creditCardService) {
        this.paymentDao = paymentDao;
        this.carService = carService;
        this.modelMapperService = modelMapperService;
        this.rentalCarService = rentalCarService;
        this.orderedAdditionalService = orderedAdditionalService;
        this.invoiceService = invoiceService;
        this.posService = posService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.creditCardService = creditCardService;
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
    public Result makePaymentForIndividualRentAdd(MakePaymentForIndividualRentAdd makePayment, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        checkAllValidationsForIndividualAdd(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

        double totalPrice = calculateTotalPrice(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

        this.posService.payment(makePayment.getCreateCreditCardRequest().getCardNumber(), makePayment.getCreateCreditCardRequest().getCardOwner(),
                                    makePayment.getCreateCreditCardRequest().getCardCvv(), makePayment.getCreateCreditCardRequest().getCardExpirationDate(), totalPrice);

        runPaymentSuccessorForIndividualRentAdd(makePayment, totalPrice, cardSaveInformation);

        return new SuccessResult("Payment, Car Rental, Additional Service adding and Invoice creation successful");
    }

    @Override
    public Result makePaymentForCorporateRentAdd(MakePaymentForCorporateRentAdd makePayment, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        checkAllValidationsForCorporateAdd(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

        double totalPrice = calculateTotalPrice(makePayment.getCreateRentalCarRequest(), makePayment.getCreateOrderedAdditionalRequestList());

        this.posService.payment(makePayment.getCreateCreditCardRequest().getCardNumber(), makePayment.getCreateCreditCardRequest().getCardOwner(),
                makePayment.getCreateCreditCardRequest().getCardCvv(), makePayment.getCreateCreditCardRequest().getCardExpirationDate(), totalPrice);

        runPaymentSuccessorForCorporateRentAdd(makePayment, totalPrice, cardSaveInformation);

        return new SuccessResult("Payment, Car Rental, Additional Service adding and Invoice creation successful");
    }

    @Override
    public Result makePaymentForIndividualRentUpdate(MakePaymentForIndividualRentUpdate makePaymentForIndividualRentUpdate, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        checkAllValidationsForIndividualUpdate(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest());

        double totalPrice = calculatePriceDifferenceWithPreviousRentalCar(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest());

        if(totalPrice > 0){
            this.posService.payment(makePaymentForIndividualRentUpdate.getCreateCreditCardRequest().getCardNumber(), makePaymentForIndividualRentUpdate.getCreateCreditCardRequest().getCardOwner(),
                    makePaymentForIndividualRentUpdate.getCreateCreditCardRequest().getCardCvv(), makePaymentForIndividualRentUpdate.getCreateCreditCardRequest().getCardExpirationDate(), totalPrice);

            runPaymentSuccessorForIndividualRentUpdate(makePaymentForIndividualRentUpdate, totalPrice, cardSaveInformation);

            return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
        }

        this.rentalCarService.updateForIndividualCustomer(makePaymentForIndividualRentUpdate.getUpdateRentalCarRequest());

        return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
    }

    @Override
    public Result makePaymentForCorporateRentUpdate(MakePaymentForCorporateRentUpdate makePaymentForCorporateRentUpdate, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        checkAllValidationsForCorporateUpdate(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest());

        double totalPrice = calculatePriceDifferenceWithPreviousRentalCar(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest());

        if(totalPrice > 0){
            this.posService.payment(makePaymentForCorporateRentUpdate.getCreateCreditCardRequest().getCardNumber(), makePaymentForCorporateRentUpdate.getCreateCreditCardRequest().getCardOwner(),
                    makePaymentForCorporateRentUpdate.getCreateCreditCardRequest().getCardCvv(), makePaymentForCorporateRentUpdate.getCreateCreditCardRequest().getCardExpirationDate(), totalPrice);

            runPaymentSuccessorForCorporateRentUpdate(makePaymentForCorporateRentUpdate, totalPrice, cardSaveInformation);

            return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
        }

        this.rentalCarService.updateForCorporateCustomer(makePaymentForCorporateRentUpdate.getUpdateRentalCarRequest());

        return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result makePaymentForRentDeliveryDateUpdate(MakePaymentForRentDeliveryDateUpdate makePaymentModel, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        checkAllValidationsForRentDeliveryDateUpdate(makePaymentModel.getUpdateDeliveryDateRequest());

        double totalPrice = calculateLateDeliveryPrice(makePaymentModel.getUpdateDeliveryDateRequest());

        this.posService.payment(makePaymentModel.getCreateCreditCardRequest().getCardNumber(), makePaymentModel.getCreateCreditCardRequest().getCardOwner(),
                makePaymentModel.getCreateCreditCardRequest().getCardCvv(), makePaymentModel.getCreateCreditCardRequest().getCardExpirationDate(), totalPrice);


        runPaymentSuccessorForRentDeliveryDateUpdate(makePaymentModel, totalPrice, cardSaveInformation);

        return new SuccessResult("Payment, Car Rental, Additional Service adding and Invoice creation successful");
    }

    @Override
    public Result makePaymentForOrderedAdditionalAdd(OrderedAdditionalAddModel orderedAdditionalAddModel, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        checkAllValidationsForOrderedAdditionalAdd(orderedAdditionalAddModel.getRentalCarId(), orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList());

        double totalPrice = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList(),
                                                                                    this.rentalCarService.getTotalDaysForRental(orderedAdditionalAddModel.getRentalCarId()));

        this.posService.payment(orderedAdditionalAddModel.getCreateCreditCardRequest().getCardNumber(), orderedAdditionalAddModel.getCreateCreditCardRequest().getCardOwner(),
                orderedAdditionalAddModel.getCreateCreditCardRequest().getCardCvv(), orderedAdditionalAddModel.getCreateCreditCardRequest().getCardExpirationDate(), totalPrice);

        runPaymentSuccessorForOrderedAdditionalAdd(orderedAdditionalAddModel, totalPrice, cardSaveInformation);

        return new SuccessResult("Payment, Additional Service adding and Invoice creation successful");
    }

    @Override
    public Result makePaymentForOrderedAdditionalUpdate(OrderedAdditionalUpdateModel orderedAdditionalUpdateModel, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        checkAllValidationsForOrderedAdditionalUpdate(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());

        double totalPrice = calculatePriceDifferenceWithPreviousOrderedAdditional(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());

        if(totalPrice > 0){
            this.posService.payment(orderedAdditionalUpdateModel.getCreateCreditCardRequest().getCardNumber(), orderedAdditionalUpdateModel.getCreateCreditCardRequest().getCardOwner(),
                    orderedAdditionalUpdateModel.getCreateCreditCardRequest().getCardCvv(), orderedAdditionalUpdateModel.getCreateCreditCardRequest().getCardExpirationDate(), totalPrice);

            runPaymentSuccessorForOrderedAdditionalUpdate(orderedAdditionalUpdateModel, totalPrice, cardSaveInformation);

            return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
        }

        this.orderedAdditionalService.update(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());

        return new SuccessResult("Payment, Additional Service adding and Invoice creation successful for update");
    }

   // @Transactional(rollbackFor = BusinessException.class) //todo:bu burada olması lazım ama buraya koyup üstten silince çalışmıyor, bak
    void runPaymentSuccessorForIndividualRentAdd(MakePaymentForIndividualRentAdd makePayment, double totalPrice, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        int rentalCarId = this.rentalCarService.addForIndividualCustomer(makePayment.getCreateRentalCarRequest());

        makePayment.getCreatePaymentRequest().setTotalPrice(totalPrice);
        makePayment.getCreatePaymentRequest().setRentalCarId(rentalCarId);
        makePayment.getCreateCreditCardRequest().setCustomerId(makePayment.getCreateRentalCarRequest().getCustomerId());

        Payment payment = this.modelMapperService.forRequest().map(makePayment.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);

        int paymentId = this.paymentDao.save(payment).getPaymentId();
        this.creditCardService.checkSaveInformationAndSaveCreditCard(makePayment.getCreateCreditCardRequest(), cardSaveInformation);
        this.orderedAdditionalService.saveOrderedAdditional(makePayment.getCreateOrderedAdditionalRequestList(), rentalCarId);
        this.invoiceService.createAndAddInvoice(rentalCarId, paymentId);
    }

    @Transactional(rollbackFor = BusinessException.class)
    void runPaymentSuccessorForCorporateRentAdd(MakePaymentForCorporateRentAdd makePayment, double totalPrice, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        int rentalCarId = this.rentalCarService.addForCorporateCustomer(makePayment.getCreateRentalCarRequest());

        makePayment.getCreatePaymentRequest().setTotalPrice(totalPrice);
        makePayment.getCreatePaymentRequest().setRentalCarId(rentalCarId);
        makePayment.getCreateCreditCardRequest().setCustomerId(makePayment.getCreateRentalCarRequest().getCustomerId());

        Payment payment = this.modelMapperService.forRequest().map(makePayment.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);

        int paymentId = this.paymentDao.save(payment).getPaymentId();
        this.creditCardService.checkSaveInformationAndSaveCreditCard(makePayment.getCreateCreditCardRequest(), cardSaveInformation);
        this.orderedAdditionalService.saveOrderedAdditional(makePayment.getCreateOrderedAdditionalRequestList(), rentalCarId);
        this.invoiceService.createAndAddInvoice(rentalCarId, paymentId);
    }

    @Transactional(rollbackFor = BusinessException.class)
    void runPaymentSuccessorForIndividualRentUpdate(MakePaymentForIndividualRentUpdate makePayment, double totalPrice, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        makePayment.getCreateCreditCardRequest().setCustomerId(makePayment.getUpdateRentalCarRequest().getCustomerId());
        makePayment.getCreatePaymentRequest().setRentalCarId(makePayment.getUpdateRentalCarRequest().getRentalCarId());
        makePayment.getCreatePaymentRequest().setTotalPrice(totalPrice);
        Payment payment = this.modelMapperService.forRequest().map(makePayment.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);


        int paymentId = this.paymentDao.save(payment).getPaymentId();
        this.rentalCarService.updateForIndividualCustomer(makePayment.getUpdateRentalCarRequest());
        this.creditCardService.checkSaveInformationAndSaveCreditCard(makePayment.getCreateCreditCardRequest(), cardSaveInformation);
        this.invoiceService.createAndAddInvoice(makePayment.getUpdateRentalCarRequest().getRentalCarId(), paymentId);
    }

    @Transactional(rollbackFor = BusinessException.class)
    void runPaymentSuccessorForCorporateRentUpdate(MakePaymentForCorporateRentUpdate makePayment, double totalPrice, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        makePayment.getCreateCreditCardRequest().setCustomerId(makePayment.getUpdateRentalCarRequest().getCustomerId());
        makePayment.getCreatePaymentRequest().setRentalCarId(makePayment.getUpdateRentalCarRequest().getRentalCarId());
        makePayment.getCreatePaymentRequest().setTotalPrice(totalPrice);
        Payment payment = this.modelMapperService.forRequest().map(makePayment.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);


        int paymentId = this.paymentDao.save(payment).getPaymentId();
        this.rentalCarService.updateForCorporateCustomer(makePayment.getUpdateRentalCarRequest());
        this.creditCardService.checkSaveInformationAndSaveCreditCard(makePayment.getCreateCreditCardRequest(), cardSaveInformation);
        this.invoiceService.createAndAddInvoice(makePayment.getUpdateRentalCarRequest().getRentalCarId(), paymentId);
    }

   // @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)   //todo:unutma
    void runPaymentSuccessorForRentDeliveryDateUpdate(MakePaymentForRentDeliveryDateUpdate makePaymentModel, double totalPrice, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        RentalCar rentalCar = this.rentalCarService.getById(makePaymentModel.getUpdateDeliveryDateRequest().getRentalCarId());

        makePaymentModel.getCreatePaymentRequest().setTotalPrice(totalPrice);
        makePaymentModel.getCreatePaymentRequest().setRentalCarId(makePaymentModel.getUpdateDeliveryDateRequest().getRentalCarId());
        makePaymentModel.getCreateCreditCardRequest().setCustomerId(rentalCar.getCustomer().getCustomerId());
        rentalCar.setFinishDate(makePaymentModel.getUpdateDeliveryDateRequest().getFinishDate());

        UpdateRentalCarRequest request = this.modelMapperService.forDto().map(rentalCar, UpdateRentalCarRequest.class);                             //(?) burada 2 kere çevirme olmuş
        request.setCustomerId(rentalCar.getCustomer().getCustomerId());
        Payment payment = this.modelMapperService.forRequest().map(makePaymentModel.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);

        int paymentId = this.paymentDao.save(payment).getPaymentId();
        this.rentalCarService.updateForIndividualCustomer(request);
        this.creditCardService.checkSaveInformationAndSaveCreditCard(makePaymentModel.getCreateCreditCardRequest(), cardSaveInformation);
        this.invoiceService.createAndAddInvoice(rentalCar.getRentalCarId(), paymentId);

    }

    @Transactional(rollbackFor = BusinessException.class)
    void runPaymentSuccessorForOrderedAdditionalAdd(OrderedAdditionalAddModel orderedAdditionalAddModel, double totalPrice, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        RentalCar rentalCar = this.rentalCarService.getById(orderedAdditionalAddModel.getRentalCarId());

        orderedAdditionalAddModel.getCreateCreditCardRequest().setCustomerId(rentalCar.getCustomer().getCustomerId());
        orderedAdditionalAddModel.getCreatePaymentRequest().setTotalPrice(totalPrice);
        orderedAdditionalAddModel.getCreatePaymentRequest().setRentalCarId(orderedAdditionalAddModel.getRentalCarId());

        Payment payment = this.modelMapperService.forRequest().map(orderedAdditionalAddModel.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);

        int paymentId = this.paymentDao.save(payment).getPaymentId();
        this.creditCardService.checkSaveInformationAndSaveCreditCard(orderedAdditionalAddModel.getCreateCreditCardRequest(), cardSaveInformation);
        this.orderedAdditionalService.saveOrderedAdditional(orderedAdditionalAddModel.getCreateOrderedAdditionalRequestList(), orderedAdditionalAddModel.getRentalCarId());
        this.invoiceService.createAndAddInvoice(orderedAdditionalAddModel.getRentalCarId(), paymentId);
    }

    @Transactional(rollbackFor = BusinessException.class)
    void runPaymentSuccessorForOrderedAdditionalUpdate(OrderedAdditionalUpdateModel orderedAdditionalUpdateModel, double totalPrice, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {

        RentalCar rentalCar = this.rentalCarService.getById(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest().getRentalCarId());

        orderedAdditionalUpdateModel.getCreateCreditCardRequest().setCustomerId(rentalCar.getCustomer().getCustomerId());
        orderedAdditionalUpdateModel.getCreatePaymentRequest().setTotalPrice(totalPrice);
        orderedAdditionalUpdateModel.getCreatePaymentRequest().setRentalCarId(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest().getRentalCarId());

        Payment payment = this.modelMapperService.forRequest().map(orderedAdditionalUpdateModel.getCreatePaymentRequest(), Payment.class);
        payment.setPaymentId(0);

        int paymentId = this.paymentDao.save(payment).getPaymentId();
        this.creditCardService.checkSaveInformationAndSaveCreditCard(orderedAdditionalUpdateModel.getCreateCreditCardRequest(), cardSaveInformation);
        this.orderedAdditionalService.update(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest());
        this.invoiceService.createAndAddInvoice(orderedAdditionalUpdateModel.getUpdateOrderedAdditionalRequest().getRentalCarId(), paymentId);
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

    private void checkAllValidationsForRentDeliveryDateUpdate(UpdateDeliveryDateRequest updateDeliveryDateRequest) throws BusinessException {

        this.rentalCarService.checkIsExistsByRentalCarId(updateDeliveryDateRequest.getRentalCarId());
        RentalCar rentalCar = this.rentalCarService.getById(updateDeliveryDateRequest.getRentalCarId());

        this.rentalCarService.checkIfFirstDateBeforeSecondDate(rentalCar.getFinishDate(),updateDeliveryDateRequest.getFinishDate());
        this.rentalCarService.checkIfCarAlreadyRentedForDeliveryDateUpdate(rentalCar.getCar().getCarId(), updateDeliveryDateRequest.getFinishDate());
        this.rentalCarService.checkIfRentedKilometerIsNotNull(rentalCar.getRentedKilometer());
        this.rentalCarService.checkIfDeliveredKilometerIsNull(rentalCar.getDeliveredKilometer());
    }


    //todo:bu burada doğrumu

    private double calculateTotalPrice(CreateRentalCarRequest rentalCarRequest, List<CreateOrderedAdditionalRequest> orderedAdditionalRequestList) throws BusinessException {

        int totalDays = this.rentalCarService.getTotalDaysForRental(rentalCarRequest.getStartDate(), rentalCarRequest.getFinishDate());
        double priceOfRental = this.rentalCarService.calculateAndReturnRentPrice(rentalCarRequest.getStartDate(), rentalCarRequest.getFinishDate(),
                this.carService.getDailyPriceByCarId(rentalCarRequest.getCarId()), rentalCarRequest.getRentedCityCityId(), rentalCarRequest.getDeliveredCityId());
        double priceOfAdditionals = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(orderedAdditionalRequestList,totalDays);

        return priceOfRental + priceOfAdditionals;
    }
    private double calculateLateDeliveryPrice(UpdateDeliveryDateRequest updateDeliveryDateRequest) throws BusinessException {

        RentalCar rentalCar = this.rentalCarService.getById(updateDeliveryDateRequest.getRentalCarId());
        int totalDays = this.rentalCarService.getTotalDaysForRental(rentalCar.getFinishDate(),updateDeliveryDateRequest.getFinishDate());

        double priceOfRental = this.rentalCarService.calculateRentalCarTotalDayPrice(rentalCar.getFinishDate(), updateDeliveryDateRequest.getFinishDate(),
                this.carService.getDailyPriceByCarId(rentalCar.getCar().getCarId()));
        double priceOfAdditionals = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(rentalCar.getRentalCarId(), totalDays);

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

    @Override
    public void checkIfExistsByPaymentId(int paymentId) throws BusinessException {
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
