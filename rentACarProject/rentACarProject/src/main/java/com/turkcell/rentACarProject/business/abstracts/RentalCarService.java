package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.RentalCarListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateRentalCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;

import java.time.LocalDate;
import java.util.List;

public interface RentalCarService {

    DataResult<List<RentalCarListDto>> getAll();

    Result add(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
    Result update(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
    Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException;

    DataResult<GetRentalCarDto> getByRentalCarId(int rentalCarId) throws BusinessException;
    DataResult<List<RentalCarListDto>> getAllByRentalCar_CarId(int carId) throws BusinessException;

    void checkIfStartDateAfterToday(LocalDate startDate) throws BusinessException;
    void checkIfStartDateBeforeFinishDate(LocalDate startDate, LocalDate finishDate) throws BusinessException;

    void checkIfCarAlreadyRentedForCreate(int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException;
    void checkIfCarAlreadyRentedForUpdate(int rentalCarId, int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException;
    void checkIfCarAlreadyRentedOnTheEnteredDate(RentalCar rentalCar, LocalDate enteredDate) throws BusinessException;                                           //4
    void checkIfCarAlreadyRentedBetweenStartAndFinishDates(RentalCar rentalCar, LocalDate startDate, LocalDate finishDate) throws BusinessException;             //5

    //for maintenance
    void checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException;
    void checkIfNotCarAlreadyRentedEnteredDate(int carId, LocalDate enteredDate) throws BusinessException;

    void checkIsExistsByRentalCarId(int rentalCarId) throws BusinessException;
    void checkIsExistsByRentalCar_CarId(int carId) throws BusinessException;
}
