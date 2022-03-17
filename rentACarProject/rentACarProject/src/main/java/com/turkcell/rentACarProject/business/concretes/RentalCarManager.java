package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.dtos.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.RentalCarListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalForRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarWithOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateRentalCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACarProject.entities.concretes.City;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalCarManager implements RentalCarService {

    private RentalCarDao rentalCarDao;
    private CarService carService;
    private CarMaintenanceService carMaintenanceService;
    private CityService cityService;
    private OrderedAdditionalService orderedAdditionalService;
    private ModelMapperService modelMapperService;

    @Autowired
    public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService, CarService carService, @Lazy CarMaintenanceService carMaintenanceService, CityService cityService, @Lazy OrderedAdditionalService orderedAdditionalService) {
        this.rentalCarDao = rentalCarDao;
        this.carService = carService;
        this.carMaintenanceService = carMaintenanceService;
        this.cityService = cityService;
        this.orderedAdditionalService = orderedAdditionalService;
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
    public Result add(CreateRentalCarRequest createRentalCarRequest) throws BusinessException {

        this.carService.checkIsExistsByCarId(createRentalCarRequest.getCarId());
        checkIfStartDateAfterToday(createRentalCarRequest.getStartDate());
        checkIfStartDateBeforeFinishDate(createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate());
        checkIfCarAlreadyRentedForCreate(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate(), createRentalCarRequest.getFinishDate());
        this.carMaintenanceService.checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(createRentalCarRequest.getCarId(), createRentalCarRequest.getStartDate());
        this.cityService.checkIfExistsByCityId(createRentalCarRequest.getRentedCityCityId());
        this.cityService.checkIfExistsByCityId(createRentalCarRequest.getDeliveredCityId());

        RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);

        calculateAndSetTotalPrice(rentalCar);

        this.rentalCarDao.save(rentalCar);

        return new SuccessResult("Rental Car Added");

    }

    @Override
    public Result addWithOrderedAdditional(CreateRentalCarWithOrderedAdditionalRequest createRentalCarWithOrderedAdditionalRequest) throws BusinessException {

        this.carService.checkIsExistsByCarId(createRentalCarWithOrderedAdditionalRequest.getCarId());
        checkIfStartDateAfterToday(createRentalCarWithOrderedAdditionalRequest.getStartDate());
        checkIfStartDateBeforeFinishDate(createRentalCarWithOrderedAdditionalRequest.getStartDate(), createRentalCarWithOrderedAdditionalRequest.getFinishDate());
        checkIfCarAlreadyRentedForCreate(createRentalCarWithOrderedAdditionalRequest.getCarId(), createRentalCarWithOrderedAdditionalRequest.getStartDate(), createRentalCarWithOrderedAdditionalRequest.getFinishDate());
        this.carMaintenanceService.checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(createRentalCarWithOrderedAdditionalRequest.getCarId(), createRentalCarWithOrderedAdditionalRequest.getStartDate());
        this.cityService.checkIfExistsByCityId(createRentalCarWithOrderedAdditionalRequest.getRentedCityId());
        this.cityService.checkIfExistsByCityId(createRentalCarWithOrderedAdditionalRequest.getDeliveredCityId());
        /*_*/
        for(int i=0;i<createRentalCarWithOrderedAdditionalRequest.getOrderedAdditionals().size();i++){          //her orderedAdditional için validation

            this.orderedAdditionalService.checkAllValidationForCreate(new CreateOrderedAdditionalForRentalCarRequest
                    (createRentalCarWithOrderedAdditionalRequest.getOrderedAdditionals().get(i).getOrderedAdditionalQuantity()
                            ,createRentalCarWithOrderedAdditionalRequest.getOrderedAdditionals().get(i).getAdditionalId()));

        }

        RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarWithOrderedAdditionalRequest, RentalCar.class);

        calculateAndSetTotalPrice(rentalCar);

        int rentalCarId = this.rentalCarDao.save(rentalCar).getRentalCarId();   //(*)

        for(int i = 0; i<createRentalCarWithOrderedAdditionalRequest.getOrderedAdditionals().size(); i++){      //her orderedAdditional için add

            Result addOperation = this.orderedAdditionalService.add(new CreateOrderedAdditionalRequest((short) createRentalCarWithOrderedAdditionalRequest.getOrderedAdditionals().get(i).getOrderedAdditionalQuantity()
                    , createRentalCarWithOrderedAdditionalRequest.getOrderedAdditionals().get(i).getAdditionalId()
                    , rentalCarId));
        }

        return new SuccessResult("Rental Car Added");

    }

    @Override
    public Result update(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {

        checkIsExistsByRentalCarId(updateRentalCarRequest.getRentalCarId());
        this.carService.checkIsExistsByCarId(updateRentalCarRequest.getCarId());
        checkIfStartDateAfterToday(updateRentalCarRequest.getStartDate());
        checkIfStartDateBeforeFinishDate(updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate());
        checkIfCarAlreadyRentedForUpdate(updateRentalCarRequest.getRentalCarId(), updateRentalCarRequest.getCarId(), updateRentalCarRequest.getStartDate(), updateRentalCarRequest.getFinishDate());
        this.carMaintenanceService.checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(updateRentalCarRequest.getCarId(), updateRentalCarRequest.getStartDate());
        this.cityService.checkIfExistsByCityId(updateRentalCarRequest.getRentedCityId());
        this.cityService.checkIfExistsByCityId(updateRentalCarRequest.getDeliveredCityId());

        RentalCar rentalCar = this.modelMapperService.forRequest().map(updateRentalCarRequest, RentalCar.class);

        calculateAndSetTotalPrice(rentalCar);

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
    public DataResult<GetRentalCarDto> getByRentalCarId(int rentalCarId) throws BusinessException {

        checkIsExistsByRentalCarId(rentalCarId);

        RentalCar rentalCar = this.rentalCarDao.getById(rentalCarId);

        GetRentalCarDto getRentalCarDto = this.modelMapperService.forDto().map(rentalCar, GetRentalCarDto.class);

        return new SuccessDataResult<>(getRentalCarDto, "Rental Car listed by rentalCarId: " + rentalCarId);

    }

    @Override
    public RentalCar getById(int rentalCarId) throws BusinessException {
        checkIsExistsByRentalCarId(rentalCarId);
        return this.rentalCarDao.getById(rentalCarId);
    }

    @Override
    public void saveChangesRentalCar(RentalCar rentalCar) throws BusinessException {
        checkIsExistsByRentalCarId(rentalCar.getRentalCarId());
        this.rentalCarDao.save(rentalCar);
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
        checkIsExistsByDeliveredCity_CityId(deliveredCity);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByDeliveredCity_CityId(deliveredCity);
        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rentals of the car listed by deliveredCity: " + deliveredCity);
    }

    @Override
    public void checkIfStartDateAfterToday(LocalDate startDate) throws BusinessException {
        if(startDate.isBefore(LocalDate.now())){
            throw new BusinessException("Start date cannot be earlier than today");
        }
    }

    @Override
    public void checkIfStartDateBeforeFinishDate(LocalDate startDate, LocalDate finishDate) throws BusinessException {
        if(finishDate.isBefore(startDate)){
            throw new BusinessException("finish date cannot be earlier than start date");
        }
    }

    @Override
    public void checkIfCarAlreadyRentedForCreate(int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException {

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

        if(rentalCars != null) {
            for(RentalCar rentalCar : rentalCars){
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar,startDate);
                checkIfCarAlreadyRentedOnTheEnteredDate(rentalCar,finishDate);
                checkIfCarAlreadyRentedBetweenStartAndFinishDates(rentalCar, startDate, finishDate);
            }
        }
    }

    @Override
    public void checkIfCarAlreadyRentedForUpdate(int rentalCarId, int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException {

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


    public void checkIfCarAlreadyRentedOnTheEnteredDate(RentalCar rentalCar, LocalDate enteredDate) throws BusinessException {
        if(rentalCar.getStartDate().isBefore(enteredDate) && (rentalCar.getFinishDate().isAfter(enteredDate))){
            throw new BusinessException("The car rented between entered dates");
        }
    }

    @Override
    public void checkIfCarAlreadyRentedBetweenStartAndFinishDates(RentalCar rentalCar,  LocalDate startDate, LocalDate finishDate) throws BusinessException {

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

    @Override
    public void checkIsExistsByRentalCarId(int rentalCarId) throws BusinessException {

        if(!this.rentalCarDao.existsByRentalCarId(rentalCarId)){
            throw new BusinessException("Rental Car id not exists, rentalCarId: " + rentalCarId);
        }
    }

    @Override
    public void checkIsExistsByRentalCar_CarId(int carId) throws BusinessException {
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

    @Override
    public void checkIsExistsByRentedCity_CityId(int rentedCityId) throws BusinessException {
        if(!this.rentalCarDao.existsByRentedCity_CityId(rentedCityId)){
            throw new BusinessException("City id not found in rental car list, rentedCityId: " + rentedCityId);
        }
    }

    @Override
    public void checkIsExistsByDeliveredCity_CityId(int deliveredCityId) throws BusinessException {
        if(!this.rentalCarDao.existsByDeliveredCity_CityId(deliveredCityId)){
            throw new BusinessException("City id not found in rental car list, deliveredCityId: " + deliveredCityId);
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

    private void calculateAndSetTotalPrice(RentalCar rentalCar) throws BusinessException {

        double totalDayPrice = calculateRentalCarTotalDayPrice(rentalCar.getStartDate(), rentalCar.getFinishDate(), this.carService.getDailyPriceByCarId(rentalCar.getCar().getCarId()));
        double totalDiffCityPrice = calculateCarDeliveredToTheSamCity(rentalCar.getRentedCity().getCityId(),rentalCar.getDeliveredCity().getCityId());

        double totalPrice = totalDayPrice + totalDiffCityPrice;

        rentalCar.setRentalCarTotalPrice(totalPrice);
        this.saveChangesRentalCar(rentalCar);

    }

    public double calculateRentalCarTotalDayPrice(LocalDate startDate, LocalDate finishDate, double dailyPrice) {

        int diff = getTotalDaysForRental(startDate, finishDate);
        double totalDayPrice = diff * dailyPrice;
        return totalDayPrice;

    }
    public int getTotalDaysForRental(LocalDate startDate, LocalDate finishDate){
        return (int) ChronoUnit.DAYS.between(startDate, finishDate);
    }

    private int calculateCarDeliveredToTheSamCity(int rentedCityId, int deliveredCityId){
        if(rentedCityId != deliveredCityId){
            return 750;
        }
        return 0;
    }

    @Override
    public void updateTotalPriceBasedOnOrderedAdditionalService(int quantity, double dailyPrice, int rentalCarId) {
        RentalCar rentalCar = this.rentalCarDao.getById(rentalCarId);
        double totalPrice = rentalCar.getRentalCarTotalPrice();

        int dayCount = (int) ChronoUnit.DAYS.between(rentalCar.getStartDate(), rentalCar.getFinishDate());
        totalPrice += quantity * dailyPrice * dayCount;
        rentalCar.setRentalCarTotalPrice(totalPrice);

    }
}
