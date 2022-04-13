package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.gets.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.lists.RentalCarListDto;
import com.turkcell.rentACarProject.business.dtos.rentalCarDtos.gets.GetRentalCarStatus;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.DeleteRentalCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rentalcars")
public class RentalCarsController {

    private RentalCarService rentalCarService;

    @Autowired
    public RentalCarsController(RentalCarService rentalCarService) {
        this.rentalCarService = rentalCarService;
    }


    @GetMapping("/getAll")
    public DataResult<List<RentalCarListDto>> getAll(){
        return this.rentalCarService.getAll();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException {
        return this.rentalCarService.delete(deleteRentalCarRequest);
    }

    @PutMapping("/deliverTheCar")
    public DataResult<GetRentalCarStatus> deliverTheCar(@RequestParam int rentalCarId, @RequestParam int carId) throws BusinessException{
        return this.rentalCarService.deliverTheCar(rentalCarId, carId);
    }

    @PutMapping("/receiveTheCar")
    public DataResult<GetRentalCarStatus> receiveTheCar(@RequestParam int rentalCarId, @RequestParam int carId, int deliveredKilometer) throws BusinessException {
        return this.rentalCarService.receiveTheCar(rentalCarId, carId, deliveredKilometer);
    }

    @GetMapping("/getById")
    public DataResult<GetRentalCarDto> getById(@RequestParam int rentalCarId) throws BusinessException {
        return this.rentalCarService.getByRentalCarId(rentalCarId);
    }

    @GetMapping("/getByRentalCar_CarId")
    public DataResult<List<RentalCarListDto>> getAllByRentalCar_CarId(@RequestParam int carId) throws BusinessException {
        return this.rentalCarService.getAllByRentalCar_CarId(carId);
    }

    @GetMapping("/getByRentedCity_CityId")
    public DataResult<List<RentalCarListDto>> getAllByRentedCity_CityId(@RequestParam int rentedCityId) throws BusinessException {
        return this.rentalCarService.getAllByRentedCity_CityId(rentedCityId);
    }

    @GetMapping("/getByDeliveredCity_CityId")
    public DataResult<List<RentalCarListDto>> getAllByDeliveredCity_CityId(@RequestParam int deliveredCityId) throws BusinessException {
        return this.rentalCarService.getAllByDeliveredCity_CityId(deliveredCityId);
    }

    @GetMapping("/getByCustomer_CustomerId")
    public DataResult<List<RentalCarListDto>> getAllByCustomer_CustomerId(@RequestParam int customerId) throws BusinessException {
        return this.rentalCarService.getAllByCustomer_CustomerId(customerId);
    }

    @GetMapping("/getByIndividualCustomer_IndividualCustomerId")
    public DataResult<List<RentalCarListDto>> getAllByIndividualCustomer_IndividualCustomerId(@RequestParam int individualCustomerId) throws BusinessException {
        return this.rentalCarService.getAllByIndividualCustomer_IndividualCustomerId(individualCustomerId);
    }

    @GetMapping("/getByCorporateCustomer_CorporateCustomerId")
    public DataResult<List<RentalCarListDto>> getAllByCorporateCustomer_CorporateCustomerId(@RequestParam int corporateCustomerId) throws BusinessException {
        return this.rentalCarService.getAllByCorporateCustomer_CorporateCustomerId(corporateCustomerId);
    }

}
