package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.api.models.rentalCar.RentalCarAddModel;
import com.turkcell.rentACarProject.business.dtos.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.RentalCarListDto;
import com.turkcell.rentACarProject.business.dtos.gets.rentalCar.GetRentalCarStatus;
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

    int addForIndividualCustomer(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
    int addForCorporateCustomer(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;

//    Result addForIndividualCustomer(RentalCarAddModel rentalCarAddModel) throws BusinessException;
//    Result addForCorporateCustomer(RentalCarAddModel rentalCarAddModel) throws BusinessException;
    Result updateForIndividualCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
    Result updateForCorporateCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
    Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException;
    DataResult<GetRentalCarStatus> deliverTheCar(int rentalCarId, int carId) throws BusinessException;
    DataResult<GetRentalCarStatus> receiveTheCar(int rentalCarId, int carId, int deliveredKilometer) throws BusinessException;

    DataResult<GetRentalCarDto> getByRentalCarId(int rentalCarId) throws BusinessException;
    RentalCar getById(int rentalCarId) throws BusinessException;
    DataResult<List<RentalCarListDto>> getAllByRentalCar_CarId(int carId) throws BusinessException;
    DataResult<List<RentalCarListDto>> getAllByRentedCity_CityId(int rentedCity) throws BusinessException;
    DataResult<List<RentalCarListDto>> getAllByDeliveredCity_CityId(int deliveredCity) throws BusinessException;
    DataResult<List<RentalCarListDto>> getAllByCustomer_CustomerId(int customerId) throws BusinessException;
    DataResult<List<RentalCarListDto>> getAllByIndividualCustomer_IndividualCustomerId(int individualCustomerId) throws BusinessException;

    DataResult<List<RentalCarListDto>> getAllByCorporateCustomer_CorporateCustomerId(int corporateCustomerId) throws BusinessException;
//    void checkIfStartDateAfterToday(LocalDate startDate) throws BusinessException;

//    void checkIfStartDateBeforeFinishDate(LocalDate startDate, LocalDate finishDate) throws BusinessException;
//    void checkIfCarAlreadyRentedForCreate(int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException;
//   void checkIfCarAlreadyRentedForUpdate(int rentalCarId, int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException;
    //for maintenance
    void checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException;

    void checkIfNotCarAlreadyRentedEnteredDate(int carId, LocalDate enteredDate) throws BusinessException;
    void checkIsExistsByRentalCarId(int rentalCarId) throws BusinessException;
//    void checkIsExistsByRentalCar_CarId(int carId) throws BusinessException;
    void checkIsNotExistsByRentalCar_CarId(int carId) throws BusinessException;
//    void checkIsExistsByRentedCity_CityId(int rentedCityId) throws BusinessException;
    void checkIsNotExistsByRentedCity_CityId(int rentedCityId) throws BusinessException;
    void checkIsNotExistsByDeliveredCity_CityId(int deliveredCityId) throws BusinessException;

    void checkIfRentalCar_CustomerIdNotExists(int customerId) throws BusinessException;
    int getTotalDaysForRental(LocalDate startDate, LocalDate finishDate);
    //todo:bunu teke indir
    int getTotalDaysForRental(int rentalCarId);
//    double calculateAndReturnTotalPrice(RentalCar rentalCar);

//    void createAndAddInvoice(int rentalCarId) throws BusinessException;
//    void checkAllValidationsForAddRent(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
    void checkAllValidationsForIndividualRentAdd(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
    void checkAllValidationsForCorporateRentAdd(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
    void checkAllValidationsForIndividualRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
    void checkAllValidationsForCorporateRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;


    double calculateRentalCarTotalDayPrice(LocalDate startDate, LocalDate finishDate, double dailyPrice);
    int calculateCarDeliveredToTheSamCity(int rentedCityId, int deliveredCityId);
    double calculateAndReturnRentPrice(LocalDate startDate, LocalDate finishDate, double carDailyPrice, int rentedCityId, int deliveredCityId);


}
