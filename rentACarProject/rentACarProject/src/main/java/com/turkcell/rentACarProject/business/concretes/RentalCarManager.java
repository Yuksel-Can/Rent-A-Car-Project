package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.gets.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.lists.*;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.gets.GetRentalCarStatusDto;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.*;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.CarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.ReturnKilometerLessThanRentKilometerException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions.CityNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.customerExceptions.CustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.individualCustomerExceptions.IndividualCustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions.RentalCarNotFoundInInvoiceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.orderedAdditionalExceptions.RentalCarAlreadyExistsInOrderedAdditionalException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.paymentExceptions.RentalCarAlreadyExistsInPaymentException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.*;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.*;
import com.turkcell.rentACarProject.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalCarManager implements RentalCarService {

    private final RentalCarDao rentalCarDao;
    private final CarService carService;
    private final CarMaintenanceService carMaintenanceService;
    private final CityService cityService;
    private final OrderedAdditionalService orderedAdditionalService;
    private final CustomerService customerService;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService, @Lazy CarService carService, @Lazy CarMaintenanceService carMaintenanceService
            , @Lazy CityService cityService, @Lazy OrderedAdditionalService orderedAdditionalService, @Lazy CustomerService customerService
            , @Lazy IndividualCustomerService individualCustomerService, @Lazy CorporateCustomerService corporateCustomerService, @Lazy InvoiceService invoiceService, @Lazy PaymentService paymentService) {

        this.rentalCarDao = rentalCarDao;
        this.carService = carService;
        this.carMaintenanceService = carMaintenanceService;
        this.cityService = cityService;
        this.orderedAdditionalService = orderedAdditionalService;
        this.customerService = customerService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<RentalCarListDto>> getAll() {

        List<RentalCar> rentalCars = this.rentalCarDao.findAll();

        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public int addForIndividualCustomer(CreateRentalCarRequest createRentalCarRequest) {

        RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);
        rentalCar.setCustomer(this.customerService.getCustomerById(createRentalCarRequest.getCustomerId()));        //(*)
        rentalCar.setRentalCarId(0);

        int rentalCarId = this.rentalCarDao.save(rentalCar).getRentalCarId();

        return rentalCarId;
    }

    @Override
    public int addForCorporateCustomer(CreateRentalCarRequest createRentalCarRequest) {

        RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);
        rentalCar.setCustomer(this.customerService.getCustomerById(createRentalCarRequest.getCustomerId()));
        rentalCar.setRentalCarId(0);

        int rentalCarId = this.rentalCarDao.save(rentalCar).getRentalCarId();

        return rentalCarId;
    }

    @Override
    public Result updateForIndividualCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws RentalCarNotFoundException {

        checkIsExistsByRentalCarId(updateRentalCarRequest.getRentalCarId());

        RentalCar rentalCar = this.modelMapperService.forRequest().map(updateRentalCarRequest, RentalCar.class);
        rentalCar.setCustomer(this.customerService.getCustomerById(updateRentalCarRequest.getCustomerId()));

        this.rentalCarDao.save(rentalCar);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateRentalCarRequest.getRentalCarId());
    }


    @Override
    public Result updateForCorporateCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws RentalCarNotFoundException {

        checkIsExistsByRentalCarId(updateRentalCarRequest.getRentalCarId());

        RentalCar rentalCar = this.modelMapperService.forRequest().map(updateRentalCarRequest, RentalCar.class);
        rentalCar.setCustomer(this.customerService.getCustomerById(updateRentalCarRequest.getCustomerId()));

        this.rentalCarDao.save(rentalCar);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateRentalCarRequest.getRentalCarId());
    }

    @Override
    public Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws RentalCarAlreadyExistsInOrderedAdditionalException, RentalCarNotFoundInInvoiceException, RentalCarAlreadyExistsInPaymentException, RentalCarNotFoundException {

        checkIsExistsByRentalCarId(deleteRentalCarRequest.getRentalCarId());
        this.orderedAdditionalService.checkIsNotExistsByOrderedAdditional_RentalCarId(deleteRentalCarRequest.getRentalCarId());
        this.paymentService.checkIfNotExistsRentalCar_RentalCarId(deleteRentalCarRequest.getRentalCarId());
        this.invoiceService.checkIfNotExistsByRentalCar_RentalCarId(deleteRentalCarRequest.getRentalCarId());

        this.rentalCarDao.deleteById(deleteRentalCarRequest.getRentalCarId());

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + deleteRentalCarRequest.getRentalCarId());

    }

    @Override
    public DataResult<GetRentalCarStatusDto> deliverTheCar(DeliverTheCarRequest deliverTheCarRequest) throws RentedKilometerAlreadyExistsException, StartDateBeforeTodayException, RentalCarNotFoundException {

        checkIfExistsRentalCarIdAndCarId(deliverTheCarRequest.getRentalCarId(), deliverTheCarRequest.getCarId());
        RentalCar rentalCar = this.rentalCarDao.getById(deliverTheCarRequest.getRentalCarId());
        checkIfStartDateAfterToday(rentalCar.getStartDate());
        checkIfRentedKilometerIsNull(rentalCar.getRentedKilometer());

        rentalCar.setRentedKilometer(rentalCar.getCar().getKilometer());
        this.rentalCarDao.save(rentalCar);

        GetRentalCarStatusDto result = this.modelMapperService.forDto().map(rentalCar, GetRentalCarStatusDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.RentalCarMessages.CAR_DELIVERED + deliverTheCarRequest.getRentalCarId());
    }

    @Override
    public DataResult<GetRentalCarStatusDto> receiveTheCar(ReceiveTheCarRequest receiveTheCarRequest) throws ReturnKilometerLessThanRentKilometerException, CarNotFoundException, DeliveredKilometerAlreadyExistsException, RentedKilometerNullException, RentalCarNotFoundException {

        checkIfExistsRentalCarIdAndCarId(receiveTheCarRequest.getRentalCarId(), receiveTheCarRequest.getCarId());
        RentalCar rentalCar = this.rentalCarDao.getById(receiveTheCarRequest.getRentalCarId());
        checkIfRentedKilometerIsNotNull(rentalCar.getRentedKilometer());
        checkIfDeliveredKilometerIsNull(rentalCar.getDeliveredKilometer());

        this.carService.updateKilometer(receiveTheCarRequest.getCarId(), receiveTheCarRequest.getDeliveredKilometer());
        rentalCar.setDeliveredKilometer(rentalCar.getCar().getKilometer());
        this.rentalCarDao.save(rentalCar);

        GetRentalCarStatusDto result = this.modelMapperService.forDto().map(rentalCar, GetRentalCarStatusDto.class);

        return new SuccessDataResult<>(result, BusinessMessages.RentalCarMessages.CAR_RECEIVED + receiveTheCarRequest.getRentalCarId());
    }

    @Override
    public DataResult<GetRentalCarDto> getByRentalCarId(int rentalCarId) throws RentalCarNotFoundException {

        checkIsExistsByRentalCarId(rentalCarId);

        RentalCar rentalCar = this.rentalCarDao.getById(rentalCarId);

        GetRentalCarDto getRentalCarDto = this.modelMapperService.forDto().map(rentalCar, GetRentalCarDto.class);

        return new SuccessDataResult<>(getRentalCarDto,BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + rentalCarId);
    }

    @Override
    public DataResult<List<RentalCarListByCarIdDto>> getAllByRentalCar_CarId(int carId) throws CarNotFoundException {

        this.carService.checkIsExistsByCarId(carId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

        List<RentalCarListByCarIdDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListByCarIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.RentalCarMessages.RENTAL_CAR_LISTED_BY_CAR_ID + carId);
    }

    @Override
    public DataResult<List<RentalCarListByRentedCityIdDto>> getAllByRentedCity_CityId(int rentedCity) throws CityNotFoundException {

        this.cityService.checkIfExistsByCityId(rentedCity);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByRentedCity_CityId(rentedCity);
        List<RentalCarListByRentedCityIdDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListByRentedCityIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.RentalCarMessages.RENTAL_CAR_LISTED_BY_RENTED_CITY_ID + rentedCity);
    }

    @Override
    public DataResult<List<RentalCarListByDeliveredCityIdDto>> getAllByDeliveredCity_CityId(int deliveredCity) throws CityNotFoundException {

        this.cityService.checkIfExistsByCityId(deliveredCity);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByDeliveredCity_CityId(deliveredCity);
        List<RentalCarListByDeliveredCityIdDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListByDeliveredCityIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.RentalCarMessages.RENTAL_CAR_LISTED_BY_DELIVERED_CITY_ID + deliveredCity);
    }

    @Override
    public DataResult<List<RentalCarListByCustomerIdDto>> getAllByCustomer_CustomerId(int customerId) throws CustomerNotFoundException {

        this.customerService.checkIfCustomerIdExists(customerId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCustomer_CustomerId(customerId);
        List<RentalCarListByCustomerIdDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListByCustomerIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.RentalCarMessages.RENTAL_CAR_LISTED_BY_CUSTOMER_ID + customerId);
    }

    @Override
    public DataResult<List<RentalCarListByIndividualCustomerIdDto>> getAllByIndividualCustomer_IndividualCustomerId(int individualCustomerId) throws IndividualCustomerNotFoundException {

        this.individualCustomerService.checkIfIndividualCustomerIdExists(individualCustomerId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCustomer_CustomerId(individualCustomerId);
        List<RentalCarListByIndividualCustomerIdDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListByIndividualCustomerIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.RentalCarMessages.RENTAL_CAR_LISTED_BY_INDIVIDUAL_CUSTOMER_ID + individualCustomerId);
    }

    @Override
    public DataResult<List<RentalCarListByCorporateCustomerIdDto>> getAllByCorporateCustomer_CorporateCustomerId(int corporateCustomerId) throws CorporateCustomerNotFoundException {

        this.corporateCustomerService.checkIfCorporateCustomerIdExists(corporateCustomerId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCustomer_CustomerId(corporateCustomerId);
        List<RentalCarListByCorporateCustomerIdDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListByCorporateCustomerIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.RentalCarMessages.RENTAL_CAR_LISTED_BY_CORPORATE_CUSTOMER_ID + corporateCustomerId);
    }



    private void checkAllCommonValidation(int carId, LocalDate startDate, LocalDate finishDate, int rentedCityId, int deliveredCityId, int customerId) throws CustomerNotFoundException, CityNotFoundException, CarAlreadyInMaintenanceException, StartDateBeforeFinishDateException, StartDateBeforeTodayException, CarNotFoundException {
        this.carService.checkIsExistsByCarId(carId);
        checkIfStartDateAfterToday(startDate);
        checkIfStartDateBeforeFinishDate(startDate, finishDate);
        this.carMaintenanceService.checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(carId, startDate);
        this.cityService.checkIfExistsByCityId(rentedCityId);
        this.cityService.checkIfExistsByCityId(deliveredCityId);
        this.customerService.checkIfCustomerIdExists(customerId);
    }

    @Override
    public void checkAllValidationsForIndividualRentAdd(CreateRentalCarRequest createRentalCarRequest) throws CarAlreadyRentedEnteredDateException, IndividualCustomerNotFoundException, CustomerNotFoundException, CityNotFoundException, CarAlreadyInMaintenanceException, StartDateBeforeTodayException, StartDateBeforeFinishDateException, CarNotFoundException {
        checkAllCommonValidation(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate(),
                createRentalCarRequest.getRentedCityCityId(), createRentalCarRequest.getDeliveredCityId(), createRentalCarRequest.getCustomerId());
        this.individualCustomerService.checkIfIndividualCustomerIdExists(createRentalCarRequest.getCustomerId());
        checkIfCarAlreadyRentedForCreate(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate());
    }

    @Override
    public void checkAllValidationsForCorporateRentAdd(CreateRentalCarRequest createRentalCarRequest) throws CarAlreadyRentedEnteredDateException, CorporateCustomerNotFoundException, CustomerNotFoundException, CityNotFoundException, StartDateBeforeTodayException, StartDateBeforeFinishDateException, CarNotFoundException, CarAlreadyInMaintenanceException {
        checkAllCommonValidation(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate(),
                createRentalCarRequest.getRentedCityCityId(), createRentalCarRequest.getDeliveredCityId(), createRentalCarRequest.getCustomerId());
        this.corporateCustomerService.checkIfCorporateCustomerIdExists(createRentalCarRequest.getCustomerId());
        checkIfCarAlreadyRentedForCreate(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate());
    }

    @Override
    public void checkAllValidationsForIndividualRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws CarAlreadyRentedEnteredDateException, IndividualCustomerNotFoundException, RentalCarNotFoundException, CustomerNotFoundException, CityNotFoundException, StartDateBeforeTodayException, StartDateBeforeFinishDateException, CarNotFoundException, CarAlreadyInMaintenanceException {
        checkIsExistsByRentalCarId(updateRentalCarRequest.getRentalCarId());
        checkAllCommonValidation(updateRentalCarRequest.getCarId(), updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate(),
                updateRentalCarRequest.getRentedCityId(), updateRentalCarRequest.getDeliveredCityId(), updateRentalCarRequest.getCustomerId());
        this.individualCustomerService.checkIfIndividualCustomerIdExists(updateRentalCarRequest.getCustomerId());
        checkIfCarAlreadyRentedForUpdate(updateRentalCarRequest.getRentalCarId(), updateRentalCarRequest.getCarId(), updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate());
    }

    @Override
    public void checkAllValidationsForCorporateRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws CarAlreadyRentedEnteredDateException, CorporateCustomerNotFoundException, RentalCarNotFoundException, CustomerNotFoundException, CityNotFoundException, StartDateBeforeTodayException, StartDateBeforeFinishDateException, CarNotFoundException, CarAlreadyInMaintenanceException {
        checkIsExistsByRentalCarId(updateRentalCarRequest.getRentalCarId());
        checkAllCommonValidation(updateRentalCarRequest.getCarId(), updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate(),
                updateRentalCarRequest.getRentedCityId(), updateRentalCarRequest.getDeliveredCityId(), updateRentalCarRequest.getCustomerId());
        this.corporateCustomerService.checkIfCorporateCustomerIdExists(updateRentalCarRequest.getCustomerId());
        checkIfCarAlreadyRentedForUpdate(updateRentalCarRequest.getRentalCarId(), updateRentalCarRequest.getCarId(), updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate());
    }

    @Override
    public double calculateAndReturnRentPrice(LocalDate startDate, LocalDate finishDate, double carDailyPrice, int rentedCityId, int deliveredCityId) {
        double totalDayPrice = calculateRentalCarTotalDayPrice(startDate, finishDate, carDailyPrice);
        double totalDiffCityPrice = calculateCarDeliveredToTheSamCity(rentedCityId, deliveredCityId);

        return totalDayPrice + totalDiffCityPrice;
    }

    @Override
    public double calculateRentalCarTotalDayPrice(LocalDate startDate, LocalDate finishDate, double carDailyPrice) {
        int day = getTotalDaysForRental(startDate, finishDate);
        return day * carDailyPrice;
    }

    @Override
    public int getTotalDaysForRental(LocalDate startDate, LocalDate finishDate){
        return (int) ChronoUnit.DAYS.between(startDate, finishDate);
    }

    @Override
    public int getTotalDaysForRental(int rentalCarId){
        RentalCar rentalCar = this.rentalCarDao.getById(rentalCarId);
        return (int) ChronoUnit.DAYS.between(rentalCar.getStartDate(), rentalCar.getFinishDate());
    }

    @Override
    public int calculateCarDeliveredToTheSamCity(int rentedCityId, int deliveredCityId){
        if(rentedCityId != deliveredCityId){
            return 750;
        }
        return 0;
    }

    @Override
    public RentalCar getById(int rentalCarId) throws RentalCarNotFoundException {
        checkIsExistsByRentalCarId(rentalCarId);
        return this.rentalCarDao.getById(rentalCarId);
    }

    private void checkIfStartDateAfterToday(LocalDate startDate) throws StartDateBeforeTodayException {
        if(startDate.isBefore(LocalDate.now())){
        throw new StartDateBeforeTodayException(BusinessMessages.RentalCarMessages.START_DATE_CANNOT_BEFORE_TODAY + startDate);
        }
    }

    private void checkIfStartDateBeforeFinishDate(LocalDate startDate, LocalDate finishDate) throws StartDateBeforeFinishDateException {
        if(finishDate.isBefore(startDate)){
            throw new StartDateBeforeFinishDateException(BusinessMessages.RentalCarMessages.FINISH_DATE_CANNOT_BEFORE_START_DATE);
        }
    }

    public void checkIfFirstDateBeforeSecondDate(LocalDate firstDate, LocalDate secondDate) throws StartDateBeforeFinishDateException {
        if(secondDate.isBefore(firstDate) || secondDate.equals(firstDate)){
            throw new StartDateBeforeFinishDateException(BusinessMessages.RentalCarMessages.SECOND_DATE_CANNOT_BEFORE_FIRST_DATE);
        }
    }

    private void checkIfCarAlreadyRentedForCreate(int carId, LocalDate startDate, LocalDate finishDate) throws CarAlreadyRentedEnteredDateException {
        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

            if(rentalCars != null) {
            for(RentalCar rentalCar : rentalCars){
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar,startDate);
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar,finishDate);
                checkIfCarAlreadyRentedBetweenStartAndFinishDates(rentalCar, startDate, finishDate);
            }
        }
    }

    private void checkIfCarAlreadyRentedForUpdate(int rentalCarId, int carId, LocalDate startDate, LocalDate finishDate) throws CarAlreadyRentedEnteredDateException {
        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);
        if(rentalCars != null) {
            for(RentalCar rentalCar : rentalCars){
                if(rentalCar.getRentalCarId() == rentalCarId) continue;
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar,startDate);
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar,finishDate);
                checkIfCarAlreadyRentedBetweenStartAndFinishDates(rentalCar, startDate, finishDate);
            }
        }
    }

    @Override
    public void checkIfCarAlreadyRentedForDeliveryDateUpdate(int carId, LocalDate enteredDate, int rentalCarId) throws CarAlreadyRentedEnteredDateException {
        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);
        if(rentalCars != null){
            for(RentalCar rentalCar : rentalCars){
                if(rentalCar.getRentalCarId() == rentalCarId){
                    continue;
                }
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar, enteredDate);
            }
        }
    }

    private void checkIfCarAlreadyRentedOnTheEnteredDate(RentalCar rentalCar, LocalDate enteredDate) throws CarAlreadyRentedEnteredDateException {
        if(rentalCar.getStartDate().isBefore(enteredDate) && (rentalCar.getFinishDate().isAfter(enteredDate))){
            throw new CarAlreadyRentedEnteredDateException(BusinessMessages.RentalCarMessages.CAR_ALREADY_RENTED_ENTERED_DATES);
        }
    }

    private void checkIfCarAlreadyRentedBetweenStartAndFinishDates(RentalCar rentalCar,  LocalDate startDate, LocalDate finishDate) throws CarAlreadyRentedEnteredDateException {

        if((rentalCar.getStartDate().isAfter(startDate) && (rentalCar.getFinishDate().isBefore(finishDate))) ||
                (rentalCar.getStartDate().equals(startDate) || (rentalCar.getFinishDate().equals(finishDate))))
        {
            throw new CarAlreadyRentedEnteredDateException(BusinessMessages.RentalCarMessages.CAR_ALREADY_RENTED_ENTERED_DATES);
        }
    }

    //for maintenance(**)
    @Override
    public void checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(int carId, LocalDate startDate, LocalDate finishDate) throws CarAlreadyRentedEnteredDateException {
    List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

        if(rentalCars != null && finishDate != null){
            for(RentalCar rentalCar : rentalCars){
                if(rentalCar.getStartDate().isAfter(startDate) && rentalCar.getFinishDate().isBefore(finishDate)){
                    throw new CarAlreadyRentedEnteredDateException(BusinessMessages.RentalCarMessages.CAR_IN_MAINTENANCE_ENTERED_DATES);
                }
            }
        }
    }
    //for maintenance(**)
    @Override
    public void checkIfNotCarAlreadyRentedEnteredDate(int carId, LocalDate enteredDate) throws CarAlreadyRentedEnteredDateException {
    List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

        if(rentalCars != null && enteredDate != null){
            for(RentalCar rentalCar : rentalCars){
                if(rentalCar.getStartDate().isBefore(enteredDate) && rentalCar.getFinishDate().isAfter(enteredDate)){
                    throw new CarAlreadyRentedEnteredDateException(BusinessMessages.RentalCarMessages.CAR_IN_MAINTENANCE_ENTERED_DATES);

                }
            }
        }
    }

    private void checkIfRentedKilometerIsNull(Integer rentedKilometer) throws RentedKilometerAlreadyExistsException {
        if(rentedKilometer != null){
            throw new RentedKilometerAlreadyExistsException(BusinessMessages.RentalCarMessages.RENTED_KILOMETER_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkIfRentedKilometerIsNotNull(Integer rentedKilometer) throws RentedKilometerNullException {
        if(rentedKilometer == null){
            throw new RentedKilometerNullException(BusinessMessages.RentalCarMessages.RENTED_KILOMETER_NULL);
        }
    }

    @Override
    public void checkIfDeliveredKilometerIsNull(Integer deliveredKilometer) throws DeliveredKilometerAlreadyExistsException {
        if(deliveredKilometer != null){
            throw new DeliveredKilometerAlreadyExistsException(BusinessMessages.RentalCarMessages.DELIVERED_KILOMETER_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkIsExistsByRentalCarId(int rentalCarId) throws RentalCarNotFoundException {

        if(!this.rentalCarDao.existsByRentalCarId(rentalCarId)){
        throw new RentalCarNotFoundException(BusinessMessages.RentalCarMessages.RENTAL_CAR_ID_NOT_FOUND + rentalCarId);
        }
    }

    @Override
    public void checkIsNotExistsByRentalCar_CarId(int carId) throws CarAlreadyExistsInRentalCarException {
        if(this.rentalCarDao.existsByCar_CarId(carId)){
        throw new CarAlreadyExistsInRentalCarException(BusinessMessages.RentalCarMessages.CAR_ID_ALREADY_EXISTS_IN_THE_RENTAL_CAR_TABLE + carId);
        }
    }

    @Override
    public void checkIfRentalCar_CustomerIdNotExists(int customerId) throws CustomerAlreadyExistsInRentalCarException {
        if (this.rentalCarDao.existsByCustomer_CustomerId(customerId)) {
            throw new CustomerAlreadyExistsInRentalCarException(BusinessMessages.RentalCarMessages.CUSTOMER_ID_ALREADY_EXISTS_IN_THE_RENTAL_CAR_TABLE + customerId);
        }
    }

    private void checkIfExistsRentalCarIdAndCarId(int rentalCarId, int carId) throws RentalCarNotFoundException {
        if(!this.rentalCarDao.existsByRentalCarIdAndCar_CarId(rentalCarId,carId)){
            throw new RentalCarNotFoundException(BusinessMessages.RentalCarMessages.RENTAL_CAR_ID_OR_CAR_ID_NOT_FOUND);
        }
    }
}
