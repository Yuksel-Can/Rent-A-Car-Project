package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.dtos.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.RentalCarListDto;
import com.turkcell.rentACarProject.business.dtos.gets.rentalCar.GetRentalCarStatus;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateRentalCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.*;
import com.turkcell.rentACarProject.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ModelMapperService modelMapperService;

    @Autowired
    public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService, @Lazy CarService carService, @Lazy CarMaintenanceService carMaintenanceService
            , @Lazy CityService cityService, @Lazy OrderedAdditionalService orderedAdditionalService, @Lazy CustomerService customerService
            , @Lazy IndividualCustomerService individualCustomerService, @Lazy CorporateCustomerService corporateCustomerService, @Lazy InvoiceService invoiceService) {

        this.rentalCarDao = rentalCarDao;
        this.carService = carService;
        this.carMaintenanceService = carMaintenanceService;
        this.cityService = cityService;
        this.orderedAdditionalService = orderedAdditionalService;
        this.customerService = customerService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.invoiceService = invoiceService;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<RentalCarListDto>> getAll() {

        List<RentalCar> rentalCars = this.rentalCarDao.findAll();

        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rentall Cars listed");
    }

    @Override
    public int addForIndividualCustomer(CreateRentalCarRequest createRentalCarRequest) throws BusinessException {

        RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);
        rentalCar.setCustomer(this.customerService.getCustomerById(createRentalCarRequest.getCustomerId()));        //(*)
        rentalCar.setRentalCarId(0);

        int rentalCarId = this.rentalCarDao.save(rentalCar).getRentalCarId();

        return rentalCarId;
    }

    @Override
    public int addForCorporateCustomer(CreateRentalCarRequest createRentalCarRequest) throws BusinessException {

        RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);
        rentalCar.setCustomer(this.customerService.getCustomerById(createRentalCarRequest.getCustomerId()));
        rentalCar.setRentalCarId(0);

        int rentalCarId = this.rentalCarDao.save(rentalCar).getRentalCarId();

        return rentalCarId;
    }

    @Override
    public Result updateForIndividualCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {

        checkIsExistsByRentalCarId(updateRentalCarRequest.getRentalCarId());

        RentalCar rentalCar = this.modelMapperService.forRequest().map(updateRentalCarRequest, RentalCar.class);
        rentalCar.setCustomer(this.customerService.getCustomerById(updateRentalCarRequest.getCustomerId()));

        this.rentalCarDao.save(rentalCar);

        return new SuccessResult("Rental Car Updated, id: " + updateRentalCarRequest.getRentalCarId());
    }


    @Override
    public Result updateForCorporateCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {

        checkIsExistsByRentalCarId(updateRentalCarRequest.getRentalCarId());

        RentalCar rentalCar = this.modelMapperService.forRequest().map(updateRentalCarRequest, RentalCar.class);
        rentalCar.setCustomer(this.customerService.getCustomerById(updateRentalCarRequest.getCustomerId()));

        this.rentalCarDao.save(rentalCar);

        return new SuccessResult("Rental Car Updated, id: " + updateRentalCarRequest.getRentalCarId());
    }

    @Override
    public Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException {

        checkIsExistsByRentalCarId(deleteRentalCarRequest.getRentalCarId());
        this.orderedAdditionalService.checkIsNotExistsByOrderedAdditional_RentalCarId(deleteRentalCarRequest.getRentalCarId());

        this.rentalCarDao.deleteById(deleteRentalCarRequest.getRentalCarId());

        return new SuccessResult("Rental Car Deleted, id: " + deleteRentalCarRequest.getRentalCarId());

    }

    @Override
    public DataResult<GetRentalCarStatus> deliverTheCar(int rentalCarId, int carId) throws BusinessException {

        checkIfExistsRentalCarIdAndCarId(rentalCarId,carId);
        RentalCar rentalCar = this.rentalCarDao.getById(rentalCarId);
        checkIfStartDateAfterToday(rentalCar.getStartDate());
        checkIfRentedKilometerIsNull(rentalCar.getRentedKilometer());

        rentalCar.setRentedKilometer(rentalCar.getCar().getKilometer());
        this.rentalCarDao.save(rentalCar);

        GetRentalCarStatus result = this.modelMapperService.forDto().map(rentalCar,GetRentalCarStatus.class);

        return new SuccessDataResult<>(result,"The car was delivered");
    }

    @Override
    public DataResult<GetRentalCarStatus> receiveTheCar(int rentalCarId, int carId, int deliveredKilometer) throws BusinessException {

        checkIfExistsRentalCarIdAndCarId(rentalCarId,carId);
        RentalCar rentalCar = this.rentalCarDao.getById(rentalCarId);
        checkIfRentedKilometerIsNotNull(rentalCar.getRentedKilometer());
        checkIfDeliveredKilometerIsNull(rentalCar.getDeliveredKilometer());

        this.carService.updateKilometer(carId, deliveredKilometer);
        rentalCar.setDeliveredKilometer(rentalCar.getCar().getKilometer());
        this.rentalCarDao.save(rentalCar);

        GetRentalCarStatus result = this.modelMapperService.forDto().map(rentalCar, GetRentalCarStatus.class);

        return new SuccessDataResult<>(result, "The car received");
    }

    @Override
    public DataResult<GetRentalCarDto> getByRentalCarId(int rentalCarId) throws BusinessException {

        checkIsExistsByRentalCarId(rentalCarId);

        RentalCar rentalCar = this.rentalCarDao.getById(rentalCarId);

        GetRentalCarDto getRentalCarDto = this.modelMapperService.forDto().map(rentalCar, GetRentalCarDto.class);

        return new SuccessDataResult<>(getRentalCarDto, "Rental Car listed by rentalCarId: " + rentalCarId);
    }

    @Override
    public DataResult<List<RentalCarListDto>> getAllByRentalCar_CarId(int carId) throws BusinessException {

        checkIsExistsByRentalCar_CarId(carId);
        this.carService.checkIsExistsByCarId(carId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rentals of the car listed by carId: " + carId);
    }

    @Override
    public DataResult<List<RentalCarListDto>> getAllByRentedCity_CityId(int rentedCity) throws BusinessException {

        this.cityService.checkIfExistsByCityId(rentedCity);
        checkIsExistsByRentedCity_CityId(rentedCity);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByRentedCity_CityId(rentedCity);
        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rentals of the car listed by rentedCityId: " + rentedCity);
    }

    @Override
    public DataResult<List<RentalCarListDto>> getAllByDeliveredCity_CityId(int deliveredCity) throws BusinessException {

        this.cityService.checkIfExistsByCityId(deliveredCity);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByDeliveredCity_CityId(deliveredCity);
        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rentals of the car listed by deliveredCity: " + deliveredCity);
    }

    @Override
    public DataResult<List<RentalCarListDto>> getAllByCustomer_CustomerId(int customerId) throws BusinessException {

        this.customerService.checkIfCustomerIdExists(customerId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCustomer_CustomerId(customerId);
        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rental of the car listed by customerId, customerId: " + customerId);
    }

    @Override
    public DataResult<List<RentalCarListDto>> getAllByIndividualCustomer_IndividualCustomerId(int individualCustomerId) throws BusinessException {

        this.individualCustomerService.checkIfIndividualCustomerIdExists(individualCustomerId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCustomer_CustomerId(individualCustomerId);
        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rental of the car listed by individualCustomerId, individualCustomerId: " + individualCustomerId);
    }

    @Override
    public DataResult<List<RentalCarListDto>> getAllByCorporateCustomer_CorporateCustomerId(int corporateCustomerId) throws BusinessException {

        this.corporateCustomerService.checkIfCorporateCustomerIdExists(corporateCustomerId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCustomer_CustomerId(corporateCustomerId);
        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rental of the car listed by corporateCustomerId, corporateCustomerId: " + corporateCustomerId);
    }



    private void checkAllCommonValidation(int carId, LocalDate startDate, LocalDate finishDate, int rentedCityId, int deliveredCityId, int customerId) throws BusinessException {
        this.carService.checkIsExistsByCarId(carId);
        checkIfStartDateAfterToday(startDate);
        checkIfStartDateBeforeFinishDate(startDate, finishDate);
        this.carMaintenanceService.checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(carId, startDate);
        this.cityService.checkIfExistsByCityId(rentedCityId);
        this.cityService.checkIfExistsByCityId(deliveredCityId);
        this.customerService.checkIfCustomerIdExists(customerId);
    }

    @Override
    public void checkAllValidationsForIndividualRentAdd(CreateRentalCarRequest createRentalCarRequest) throws BusinessException {
        checkAllCommonValidation(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate(),
                createRentalCarRequest.getRentedCityCityId(), createRentalCarRequest.getDeliveredCityId(), createRentalCarRequest.getCustomerId());
        this.individualCustomerService.checkIfIndividualCustomerIdExists(createRentalCarRequest.getCustomerId());
        checkIfCarAlreadyRentedForCreate(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate());
    }

    @Override
    public void checkAllValidationsForCorporateRentAdd(CreateRentalCarRequest createRentalCarRequest) throws BusinessException {
        checkAllCommonValidation(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate(),
                createRentalCarRequest.getRentedCityCityId(), createRentalCarRequest.getDeliveredCityId(), createRentalCarRequest.getCustomerId());
        this.corporateCustomerService.checkIfCorporateCustomerIdExists(createRentalCarRequest.getCustomerId());
        checkIfCarAlreadyRentedForCreate(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate());
    }

    @Override
    public void checkAllValidationsForIndividualRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {
        checkIsExistsByRentalCarId(updateRentalCarRequest.getRentalCarId());
        checkAllCommonValidation(updateRentalCarRequest.getCarId(), updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate(),
                updateRentalCarRequest.getRentedCityId(), updateRentalCarRequest.getDeliveredCityId(), updateRentalCarRequest.getCustomerId());
        this.individualCustomerService.checkIfIndividualCustomerIdExists(updateRentalCarRequest.getCustomerId());
        checkIfCarAlreadyRentedForUpdate(updateRentalCarRequest.getRentalCarId(), updateRentalCarRequest.getCarId(), updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate());
    }

    @Override
    public void checkAllValidationsForCorporateRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {
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
    public RentalCar getById(int rentalCarId) throws BusinessException {
        return this.rentalCarDao.getById(rentalCarId);
    }

    private void checkIfStartDateAfterToday(LocalDate startDate) throws BusinessException {
        if(startDate.isBefore(LocalDate.now())){
        throw new BusinessException("Start date cannot be earlier than today");
        }
    }

    private void checkIfStartDateBeforeFinishDate(LocalDate startDate, LocalDate finishDate) throws BusinessException {
        if(finishDate.isBefore(startDate)){
            throw new BusinessException("finish date cannot be earlier than start date");
        }
    }
    //todo:alttaki üsttekinin kopyası oldu
    public void checkIfFirstDateBeforeSecondDate(LocalDate firstDate, LocalDate secondDate) throws BusinessException {
        if(secondDate.isBefore(firstDate) || secondDate.equals(firstDate)){
            throw new BusinessException("second date cannot be before first date or cannot equal");
        }
    }

    private void checkIfCarAlreadyRentedForCreate(int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException {
        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

            if(rentalCars != null) {
            for(RentalCar rentalCar : rentalCars){
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar,startDate);
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar,finishDate);
                checkIfCarAlreadyRentedBetweenStartAndFinishDates(rentalCar, startDate, finishDate);
            }
        }
    }
        //todo:bunlar teke nasıl düşer
    private void checkIfCarAlreadyRentedForUpdate(int rentalCarId, int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException {
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
    public void checkIfCarAlreadyRentedForDeliveryDateUpdate(int carId, LocalDate enteredDate) throws BusinessException {
        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);
        if(rentalCars != null){
            for(RentalCar rentalCar : rentalCars){
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar, enteredDate);
            }
        }
    }

    //todo:buraya parametre yanlış
    private void checkIfCarAlreadyRentedOnTheEnteredDate(RentalCar rentalCar, LocalDate enteredDate) throws BusinessException {
        if(rentalCar.getStartDate().isBefore(enteredDate) && (rentalCar.getFinishDate().isAfter(enteredDate))){
            throw new BusinessException("The car rented between entered dates");
        }
    }
    //todo:buda(parametre)
    private void checkIfCarAlreadyRentedBetweenStartAndFinishDates(RentalCar rentalCar,  LocalDate startDate, LocalDate finishDate) throws BusinessException {

        if((rentalCar.getStartDate().isAfter(startDate) && (rentalCar.getFinishDate().isBefore(finishDate))) ||
                (rentalCar.getStartDate().equals(startDate) || (rentalCar.getFinishDate().equals(finishDate))))
        {
            throw new BusinessException("The car is already rented between start and finish date");
        }
    }

    //for maintenance(*)
    @Override
    public void checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException {
    List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

        if(rentalCars != null && finishDate != null){
            for(RentalCar rentalCar : rentalCars){
                if(rentalCar.getStartDate().isAfter(startDate) && rentalCar.getFinishDate().isBefore(finishDate)){
                    throw new BusinessException("The car is rented between start and finish date, cannot be maintenance or rented");
                }
            }
        }
    }
    //for maintenance(*)
    @Override
    public void checkIfNotCarAlreadyRentedEnteredDate(int carId, LocalDate enteredDate) throws BusinessException {
    List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

        if(rentalCars != null && enteredDate != null){
            for(RentalCar rentalCar : rentalCars){
                if(rentalCar.getStartDate().isBefore(enteredDate) && rentalCar.getFinishDate().isAfter(enteredDate)){
                    throw new BusinessException("The car is rented between these dates, cannot be maintenance or rented");

                }
            }
        }
    }

    private void checkIfRentedKilometerIsNull(Integer rentedKilometer) throws BusinessException {
        if(rentedKilometer != null){
            throw new BusinessException("The rented kilometer is already exists, the car has already been rented");
        }
    }

    @Override
    public void checkIfRentedKilometerIsNotNull(Integer rentedKilometer) throws BusinessException {
        if(rentedKilometer == null){
            throw new BusinessException("The car has not yet been delivered to the customer, the rented kilometer cannot be null");
        }
    }

    @Override
    public void checkIfDeliveredKilometerIsNull(Integer deliveredKilometer) throws BusinessException {
        if(deliveredKilometer != null){
            throw new BusinessException("The delivered kilometer is already exists, the car has already been delivered, deliveredKilometer: " + deliveredKilometer);
        }
    }

    @Override
    public void checkIsExistsByRentalCarId(int rentalCarId) throws BusinessException {

        if(!this.rentalCarDao.existsByRentalCarId(rentalCarId)){
        throw new BusinessException("Rental Car id not exists, rentalCarId: " + rentalCarId);
        }
    }

    private void checkIsExistsByRentalCar_CarId(int carId) throws BusinessException {
        if(!this.rentalCarDao.existsByCar_CarId(carId)){
        throw new BusinessException("Car id not found in rental car list, carId: " + carId);
        }
    }

    @Override
    public void checkIsNotExistsByRentalCar_CarId(int carId) throws BusinessException {
        if(this.rentalCarDao.existsByCar_CarId(carId)){
        throw new BusinessException("Car id not found in rental car list, carId: " + carId);
        }
    }

    private void checkIsExistsByRentedCity_CityId(int rentedCityId) throws BusinessException {
        if(!this.rentalCarDao.existsByRentedCity_CityId(rentedCityId)){
        throw new BusinessException("City id not found in rental car list, rentedCityId: " + rentedCityId);
        }

    }

    @Override
    public void checkIsNotExistsByRentedCity_CityId(int rentedCityId) throws BusinessException {
        if(this.rentalCarDao.existsByRentedCity_CityId(rentedCityId)){
        throw new BusinessException("City id exists in rental car list, rentedCityId: " + rentedCityId);
        }
    }

    @Override
    public void checkIsNotExistsByDeliveredCity_CityId(int deliveredCityId) throws BusinessException {
        if(this.rentalCarDao.existsByDeliveredCity_CityId(deliveredCityId)){
        throw new BusinessException("City id exists in rental car list, deliveredCityId: " + deliveredCityId);
        }
    }

    @Override
    public void checkIfRentalCar_CustomerIdNotExists(int customerId) throws BusinessException {
        if (this.rentalCarDao.existsByCustomer_CustomerId(customerId)) {
            throw new BusinessException("Customer id exists in rental car list, customerId: " + customerId);
        }
    }

    private void checkIfExistsRentalCarIdAndCarId(int rentalCarId, int carId) throws BusinessException {
        if(!this.rentalCarDao.existsByRentalCarIdAndCar_CarId(rentalCarId,carId)){
            throw new BusinessException("Rental car id and car id not found rental car table, rentalCarId: " + rentalCarId + ", carId: " + carId);
        }
    }
}
