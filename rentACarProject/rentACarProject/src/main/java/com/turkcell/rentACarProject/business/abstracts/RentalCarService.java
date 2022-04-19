package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.gets.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.lists.*;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.gets.GetRentalCarStatusDto;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.*;
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

    Result updateForIndividualCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
    Result updateForCorporateCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
    Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException;
    DataResult<GetRentalCarStatusDto> deliverTheCar(DeliverTheCarRequest deliverTheCarRequest) throws BusinessException;
    DataResult<GetRentalCarStatusDto> receiveTheCar(ReceiveTheCarRequest receiveTheCarRequest) throws BusinessException;

    DataResult<GetRentalCarDto> getByRentalCarId(int rentalCarId) throws BusinessException;
    RentalCar getById(int rentalCarId) throws BusinessException;
    DataResult<List<RentalCarListByCarIdDto>> getAllByRentalCar_CarId(int carId) throws BusinessException;
    DataResult<List<RentalCarListByRentedCityIdDto>> getAllByRentedCity_CityId(int rentedCity) throws BusinessException;
    DataResult<List<RentalCarListByDeliveredCityIdDto>> getAllByDeliveredCity_CityId(int deliveredCity) throws BusinessException;
    DataResult<List<RentalCarListByCustomerIdDto>> getAllByCustomer_CustomerId(int customerId) throws BusinessException;
    DataResult<List<RentalCarListByIndividualCustomerIdDto>> getAllByIndividualCustomer_IndividualCustomerId(int individualCustomerId) throws BusinessException;
    DataResult<List<RentalCarListByCorporateCustomerIdDto>> getAllByCorporateCustomer_CorporateCustomerId(int corporateCustomerId) throws BusinessException;

    //for maintenance
    void checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(int carId, LocalDate startDate, LocalDate finishDate) throws BusinessException;

    void checkIfNotCarAlreadyRentedEnteredDate(int carId, LocalDate enteredDate) throws BusinessException;
    void checkIsExistsByRentalCarId(int rentalCarId) throws BusinessException;
    void checkIsNotExistsByRentalCar_CarId(int carId) throws BusinessException;

    void checkIfFirstDateBeforeSecondDate(LocalDate firstDate, LocalDate secondDate) throws BusinessException;
    void checkIfCarAlreadyRentedForDeliveryDateUpdate(int carId, LocalDate enteredDate) throws BusinessException;

    void checkIfRentedKilometerIsNotNull(Integer rentedKilometer) throws BusinessException;
    void checkIfDeliveredKilometerIsNull(Integer deliveredKilometer) throws BusinessException;


    void checkIfRentalCar_CustomerIdNotExists(int customerId) throws BusinessException;
    int getTotalDaysForRental(LocalDate startDate, LocalDate finishDate);
    //todo:bunu teke indir
    int getTotalDaysForRental(int rentalCarId);

    void checkAllValidationsForIndividualRentAdd(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
    void checkAllValidationsForCorporateRentAdd(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
    void checkAllValidationsForIndividualRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
    void checkAllValidationsForCorporateRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;


    double calculateRentalCarTotalDayPrice(LocalDate startDate, LocalDate finishDate, double dailyPrice);
    int calculateCarDeliveredToTheSamCity(int rentedCityId, int deliveredCityId);
    double calculateAndReturnRentPrice(LocalDate startDate, LocalDate finishDate, double carDailyPrice, int rentedCityId, int deliveredCityId);


}
