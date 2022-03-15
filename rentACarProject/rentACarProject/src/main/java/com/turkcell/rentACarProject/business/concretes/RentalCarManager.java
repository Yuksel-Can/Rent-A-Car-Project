package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.RentalCarListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateRentalCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalCarManager implements RentalCarService {

    private RentalCarDao rentalCarDao;
    private CarService carService;
    private CarMaintenanceService carMaintenanceService;
    private CityService cityService;
    private ModelMapperService modelMapperService;

    @Autowired
    public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService, CarService carService,@Lazy CarMaintenanceService carMaintenanceService, CityService cityService) {
        this.rentalCarDao = rentalCarDao;
        this.carService = carService;
        this.carMaintenanceService = carMaintenanceService;
        this.cityService = cityService;
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
        this.cityService.checkIfExistsByCityId(createRentalCarRequest.getRentedCityId());
        this.cityService.checkIfExistsByCityId(createRentalCarRequest.getDeliveredCityId());

        RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);

        this.rentalCarDao.save(rentalCar);

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

        this.rentalCarDao.save(rentalCar);

        return new SuccessResult("Rental Car Updated, id: " + updateRentalCarRequest.getRentalCarId());

    }

    @Override
    public Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException {

        checkIsExistsByRentalCarId(deleteRentalCarRequest.getRentalCarId());

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
    public DataResult<List<RentalCarListDto>> getAllByRentalCar_CarId(int carId) throws BusinessException {

        checkIsExistsByRentalCar_CarId(carId);
        this.carService.checkIsExistsByCarId(carId);

        List<RentalCar> rentalCars = this.rentalCarDao.getAllByCar_CarId(carId);

        List<RentalCarListDto> result = rentalCars.stream().map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Rentals of the car listed by carId: " + carId);

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
            throw new BusinessException("Rental Car id not exists");
        }
    }

    @Override
    public void checkIsExistsByRentalCar_CarId(int carId) throws BusinessException {
        if(!this.rentalCarDao.existsByCar_CarId(carId)){
            throw new BusinessException("Car id not found in rental car list, carId: " + carId);
        }
    }


}
