package com.turkcell.rentACarProject.business.abstracts;

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
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;

import java.time.LocalDate;
import java.util.List;

public interface RentalCarService {

    DataResult<List<RentalCarListDto>> getAll();

    int addForIndividualCustomer(CreateRentalCarRequest createRentalCarRequest);
    int addForCorporateCustomer(CreateRentalCarRequest createRentalCarRequest);

    Result updateForIndividualCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws RentalCarNotFoundException;
    Result updateForCorporateCustomer(UpdateRentalCarRequest updateRentalCarRequest) throws RentalCarNotFoundException;
    Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws RentalCarAlreadyExistsInOrderedAdditionalException, RentalCarNotFoundInInvoiceException, RentalCarAlreadyExistsInPaymentException, RentalCarNotFoundException;
    DataResult<GetRentalCarStatusDto> deliverTheCar(DeliverTheCarRequest deliverTheCarRequest) throws RentedKilometerAlreadyExistsException, StartDateBeforeTodayException, RentalCarNotFoundException;
    DataResult<GetRentalCarStatusDto> receiveTheCar(ReceiveTheCarRequest receiveTheCarRequest) throws ReturnKilometerLessThanRentKilometerException, CarNotFoundException, DeliveredKilometerAlreadyExistsException, RentedKilometerNullException, RentalCarNotFoundException;

    DataResult<GetRentalCarDto> getByRentalCarId(int rentalCarId) throws RentalCarNotFoundException;
    RentalCar getById(int rentalCarId) throws RentalCarNotFoundException;
    DataResult<List<RentalCarListByCarIdDto>> getAllByRentalCar_CarId(int carId) throws CarNotFoundException;
    DataResult<List<RentalCarListByRentedCityIdDto>> getAllByRentedCity_CityId(int rentedCity) throws CityNotFoundException;
    DataResult<List<RentalCarListByDeliveredCityIdDto>> getAllByDeliveredCity_CityId(int deliveredCity) throws CityNotFoundException;
    DataResult<List<RentalCarListByCustomerIdDto>> getAllByCustomer_CustomerId(int customerId) throws CustomerNotFoundException;
    DataResult<List<RentalCarListByIndividualCustomerIdDto>> getAllByIndividualCustomer_IndividualCustomerId(int individualCustomerId) throws IndividualCustomerNotFoundException;
    DataResult<List<RentalCarListByCorporateCustomerIdDto>> getAllByCorporateCustomer_CorporateCustomerId(int corporateCustomerId) throws CorporateCustomerNotFoundException;

    //for maintenance
    void checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(int carId, LocalDate startDate, LocalDate finishDate) throws StartDateBeforeTodayException, CarAlreadyRentedEnteredDateException;

    void checkIfNotCarAlreadyRentedEnteredDate(int carId, LocalDate enteredDate) throws CarAlreadyRentedEnteredDateException;
    void checkIsExistsByRentalCarId(int rentalCarId) throws RentalCarNotFoundException;
    void checkIsNotExistsByRentalCar_CarId(int carId) throws CarAlreadyExistsInRentalCarException;

    void checkIfFirstDateBeforeSecondDate(LocalDate firstDate, LocalDate secondDate) throws StartDateBeforeFinishDateException;
    void checkIfCarAlreadyRentedForDeliveryDateUpdate(int carId, LocalDate enteredDate, int rentalCarId) throws CarAlreadyRentedEnteredDateException;

    void checkIfRentedKilometerIsNotNull(Integer rentedKilometer) throws RentedKilometerNullException;
    void checkIfDeliveredKilometerIsNull(Integer deliveredKilometer) throws DeliveredKilometerAlreadyExistsException;


    void checkIfRentalCar_CustomerIdNotExists(int customerId) throws CustomerAlreadyExistsInRentalCarException;
    int getTotalDaysForRental(LocalDate startDate, LocalDate finishDate);
    int getTotalDaysForRental(int rentalCarId);

    void checkAllValidationsForIndividualRentAdd(CreateRentalCarRequest createRentalCarRequest) throws CarAlreadyRentedEnteredDateException, IndividualCustomerNotFoundException, CustomerNotFoundException, CityNotFoundException, CarAlreadyInMaintenanceException, StartDateBeforeTodayException, StartDateBeforeFinishDateException, CarNotFoundException;
    void checkAllValidationsForCorporateRentAdd(CreateRentalCarRequest createRentalCarRequest) throws CarAlreadyRentedEnteredDateException, CorporateCustomerNotFoundException, CustomerNotFoundException, CityNotFoundException, StartDateBeforeTodayException, StartDateBeforeFinishDateException, CarNotFoundException, CarAlreadyInMaintenanceException;
    void checkAllValidationsForIndividualRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws CarAlreadyRentedEnteredDateException, IndividualCustomerNotFoundException, RentalCarNotFoundException, CustomerNotFoundException, CityNotFoundException, StartDateBeforeTodayException, StartDateBeforeFinishDateException, CarNotFoundException, CarAlreadyInMaintenanceException;
    void checkAllValidationsForCorporateRentUpdate(UpdateRentalCarRequest updateRentalCarRequest) throws CarAlreadyRentedEnteredDateException, CorporateCustomerNotFoundException, RentalCarNotFoundException, CustomerNotFoundException, CityNotFoundException, StartDateBeforeTodayException, StartDateBeforeFinishDateException, CarNotFoundException, CarAlreadyInMaintenanceException;


    double calculateRentalCarTotalDayPrice(LocalDate startDate, LocalDate finishDate, double dailyPrice);
    int calculateCarDeliveredToTheSamCity(int rentedCityId, int deliveredCityId);
    double calculateAndReturnRentPrice(LocalDate startDate, LocalDate finishDate, double carDailyPrice, int rentedCityId, int deliveredCityId);


}
