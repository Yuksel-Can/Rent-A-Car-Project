package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.lists.CarMaintenanceListByCarIdDto;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.lists.CarMaintenanceListDto;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.gets.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.CarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarExistsInCarMaintenanceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarMaintenanceNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.MaintenanceReturnDateBeforeTodayException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.CarAlreadyRentedEnteredDateException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.StartDateBeforeTodayException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private final CarMaintenanceDao carMaintenanceDao;
    private final CarService carService;
    private final RentalCarService rentalCarService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, CarService carService, RentalCarService rentalCarService, ModelMapperService modelMapperService){
        this.carMaintenanceDao = carMaintenanceDao;
        this.carService = carService;
        this.rentalCarService = rentalCarService;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> result = carMaintenances.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws MaintenanceReturnDateBeforeTodayException, CarAlreadyInMaintenanceException, CarNotFoundException, CarAlreadyRentedEnteredDateException, StartDateBeforeTodayException {

        checkAllValidationForCarMaintenanceForAdd(createCarMaintenanceRequest.getReturnDate(),createCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setMaintenanceId(0);

        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws CarMaintenanceNotFoundException, MaintenanceReturnDateBeforeTodayException, CarAlreadyInMaintenanceException, CarNotFoundException, CarAlreadyRentedEnteredDateException, StartDateBeforeTodayException {

        checkIsExistsByCarMaintenanceId(updateCarMaintenanceRequest.getMaintenanceId());
        checkAllValidationForCarMaintenanceForUpdate(updateCarMaintenanceRequest.getReturnDate(), updateCarMaintenanceRequest.getCarId(), updateCarMaintenanceRequest.getMaintenanceId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);

        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateCarMaintenanceRequest.getMaintenanceId());
    }

    @Override
    public Result delete(DeleteCarMaintenanceRequest carMaintenanceRequest) throws CarMaintenanceNotFoundException {

        checkIsExistsByCarMaintenanceId(carMaintenanceRequest.getMaintenanceId());

        this.carMaintenanceDao.deleteById(carMaintenanceRequest.getMaintenanceId());

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + carMaintenanceRequest.getMaintenanceId());
    }

    @Override
    public DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId) throws CarMaintenanceNotFoundException {

        checkIsExistsByCarMaintenanceId(carMaintenanceId);

        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);
        GetCarMaintenanceDto result = this.modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class);

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + carMaintenanceId);
    }

    @Override
    public DataResult<List<CarMaintenanceListByCarIdDto>> getAllByCarMaintenance_CarId(int carId) throws CarNotFoundException {

        this.carService.checkIsExistsByCarId(carId);

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAllByCar_CarId(carId);

        List<CarMaintenanceListByCarIdDto> result = carMaintenances.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListByCarIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_LISTED_BY_CAR_ID + carId);
    }

    private void checkAllValidationForCarMaintenanceForAdd(LocalDate returnDate, int carId) throws CarAlreadyInMaintenanceException, MaintenanceReturnDateBeforeTodayException, CarNotFoundException, CarAlreadyRentedEnteredDateException, StartDateBeforeTodayException {

        checkIfNotReturnDateBeforeToday(returnDate);
        this.carService.checkIsExistsByCarId(carId);
        checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(carId, LocalDate.now());

        this.rentalCarService.checkIfNotCarAlreadyRentedEnteredDate(carId,LocalDate.now());
        this.rentalCarService.checkIfNotCarAlreadyRentedEnteredDate(carId,returnDate);
        this.rentalCarService.checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(carId, LocalDate.now(), returnDate);
    }

    private void checkAllValidationForCarMaintenanceForUpdate(LocalDate returnDate, int carId, int maintenanceId) throws CarAlreadyInMaintenanceException, MaintenanceReturnDateBeforeTodayException, CarNotFoundException, CarAlreadyRentedEnteredDateException, StartDateBeforeTodayException {

        checkIfNotReturnDateBeforeToday(returnDate);
        this.carService.checkIsExistsByCarId(carId);
        checkIfNotCarAlreadyInMaintenanceOnTheEnteredDateForUpdate(carId, LocalDate.now(), maintenanceId);

        this.rentalCarService.checkIfNotCarAlreadyRentedEnteredDate(carId,LocalDate.now());
        this.rentalCarService.checkIfNotCarAlreadyRentedEnteredDate(carId,returnDate);
        this.rentalCarService.checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(carId, LocalDate.now(), returnDate);
    }

    private void checkIfNotReturnDateBeforeToday(LocalDate returnDate) throws MaintenanceReturnDateBeforeTodayException {
        if(returnDate != null){
            if(returnDate.isBefore(LocalDate.now())){
                throw new MaintenanceReturnDateBeforeTodayException(BusinessMessages.CarMaintenanceMessages.RETURN_DATE_CANNOT_BEFORE_TODAY + returnDate);
            }
        }
    }

    @Override
    public void checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(int carId, LocalDate enteredDate) throws CarAlreadyInMaintenanceException {

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAllByCar_CarId(carId);

        if(carMaintenances != null){
            for (CarMaintenance carMaintenance : carMaintenances){
                if (carMaintenance.getReturnDate()==null){
                    if(carMaintenance.getReturnDate().now().plusDays(14).isAfter(enteredDate)){
                        throw new CarAlreadyInMaintenanceException(BusinessMessages.CarMaintenanceMessages.CAR_ALREADY_IN_MAINTENANCE);
                    }
                }else if(carMaintenance.getReturnDate().isAfter(enteredDate)){
                    throw new CarAlreadyInMaintenanceException(BusinessMessages.CarMaintenanceMessages.CAR_ALREADY_IN_MAINTENANCE);
                }
            }
        }
    }

    public void checkIfNotCarAlreadyInMaintenanceOnTheEnteredDateForUpdate(int carId, LocalDate enteredDate, int maintenanceId) throws CarAlreadyInMaintenanceException {

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAllByCar_CarId(carId);

        if(carMaintenances != null){
            for (CarMaintenance carMaintenance : carMaintenances){
                if(carMaintenance.getMaintenanceId() == maintenanceId){
                    continue;
                }
                if (carMaintenance.getReturnDate()==null){
                    if(carMaintenance.getReturnDate().now().plusDays(14).isAfter(enteredDate)){
                        throw new CarAlreadyInMaintenanceException(BusinessMessages.CarMaintenanceMessages.CAR_ALREADY_IN_MAINTENANCE);
                    }
                }else if(carMaintenance.getReturnDate().isAfter(enteredDate)){
                    throw new CarAlreadyInMaintenanceException(BusinessMessages.CarMaintenanceMessages.CAR_ALREADY_IN_MAINTENANCE);
                }
            }
        }
    }

    private void checkIsExistsByCarMaintenanceId(int carMaintenanceId) throws CarMaintenanceNotFoundException {
        if(!this.carMaintenanceDao.existsByMaintenanceId(carMaintenanceId)){
            throw new CarMaintenanceNotFoundException(BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_ID_NOT_FOUND + carMaintenanceId);
        }
    }

    @Override
    public void checkIsExistsByCar_CarId(int carId) throws CarExistsInCarMaintenanceException {
        if(this.carMaintenanceDao.existsByCar_CarId(carId)){
            throw new CarExistsInCarMaintenanceException(BusinessMessages.CarMaintenanceMessages.CAR_ID_ALREADY_EXISTS_IN_THE_CAR_MAINTENANCE_TABLE + carId);
        }
    }

}
