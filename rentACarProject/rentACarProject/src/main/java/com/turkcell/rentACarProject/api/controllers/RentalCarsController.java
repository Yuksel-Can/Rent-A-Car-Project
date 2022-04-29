package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.gets.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.lists.*;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.gets.GetRentalCarStatusDto;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.DeleteRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.DeliverTheCarRequest;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.ReceiveTheCarRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.CarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.ReturnKilometerLessThanRentKilometerException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rentalCars")
public class RentalCarsController {

    private final RentalCarService rentalCarService;

    @Autowired
    public RentalCarsController(RentalCarService rentalCarService) {
        this.rentalCarService = rentalCarService;
    }


    @GetMapping("/getAll")
    public DataResult<List<RentalCarListDto>> getAll(){
        return this.rentalCarService.getAll();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteRentalCarRequest deleteRentalCarRequest) throws RentalCarAlreadyExistsInOrderedAdditionalException, RentalCarNotFoundInInvoiceException, RentalCarAlreadyExistsInPaymentException, RentalCarNotFoundException {
        return this.rentalCarService.delete(deleteRentalCarRequest);
    }

    @PutMapping("/deliverTheCar")
    public DataResult<GetRentalCarStatusDto> deliverTheCar(@RequestBody @Valid DeliverTheCarRequest deliverTheCarRequest) throws StartDateBeforeTodayException, RentedKilometerAlreadyExistsException, RentalCarNotFoundException {
        return this.rentalCarService.deliverTheCar(deliverTheCarRequest);
    }

    @PutMapping("/receiveTheCar")
    public DataResult<GetRentalCarStatusDto> receiveTheCar(@RequestBody @Valid ReceiveTheCarRequest receiveTheCarRequest) throws DeliveredKilometerAlreadyExistsException, ReturnKilometerLessThanRentKilometerException, CarNotFoundException, RentalCarNotFoundException, RentedKilometerNullException {
        return this.rentalCarService.receiveTheCar(receiveTheCarRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetRentalCarDto> getById(@RequestParam int rentalCarId) throws RentalCarNotFoundException {
        return this.rentalCarService.getByRentalCarId(rentalCarId);
    }

    @GetMapping("/getByRentalCar_CarId")
    public DataResult<List<RentalCarListByCarIdDto>> getAllByRentalCar_CarId(@RequestParam int carId) throws CarNotFoundException {
        return this.rentalCarService.getAllByRentalCar_CarId(carId);
    }

    @GetMapping("/getByRentedCity_CityId")
    public DataResult<List<RentalCarListByRentedCityIdDto>> getAllByRentedCity_CityId(@RequestParam int rentedCityId) throws CityNotFoundException {
        return this.rentalCarService.getAllByRentedCity_CityId(rentedCityId);
    }

    @GetMapping("/getByDeliveredCity_CityId")
    public DataResult<List<RentalCarListByDeliveredCityIdDto>> getAllByDeliveredCity_CityId(@RequestParam int deliveredCityId) throws CityNotFoundException {
        return this.rentalCarService.getAllByDeliveredCity_CityId(deliveredCityId);
    }

    @GetMapping("/getByCustomer_CustomerId")
    public DataResult<List<RentalCarListByCustomerIdDto>> getAllByCustomer_CustomerId(@RequestParam int customerId) throws CustomerNotFoundException {
        return this.rentalCarService.getAllByCustomer_CustomerId(customerId);
    }

    @GetMapping("/getByIndividualCustomer_IndividualCustomerId")
    public DataResult<List<RentalCarListByIndividualCustomerIdDto>> getAllByIndividualCustomer_IndividualCustomerId(@RequestParam int individualCustomerId) throws IndividualCustomerNotFoundException {
        return this.rentalCarService.getAllByIndividualCustomer_IndividualCustomerId(individualCustomerId);
    }

    @GetMapping("/getByCorporateCustomer_CorporateCustomerId")
    public DataResult<List<RentalCarListByCorporateCustomerIdDto>> getAllByCorporateCustomer_CorporateCustomerId(@RequestParam int corporateCustomerId) throws CorporateCustomerNotFoundException {
        return this.rentalCarService.getAllByCorporateCustomer_CorporateCustomerId(corporateCustomerId);
    }

}
