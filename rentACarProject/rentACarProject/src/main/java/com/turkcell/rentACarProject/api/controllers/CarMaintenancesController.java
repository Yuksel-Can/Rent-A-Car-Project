package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.lists.CarMaintenanceListByCarIdDto;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.lists.CarMaintenanceListDto;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.gets.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.CarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarMaintenanceNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.MaintenanceReturnDateBeforeTodayException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.CarAlreadyRentedEnteredDateException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.StartDateBeforeTodayException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {

    private final CarMaintenanceService carMaintenanceService;

    @Autowired
    public CarMaintenancesController(CarMaintenanceService carMaintenanceService){
        this.carMaintenanceService = carMaintenanceService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        return this.carMaintenanceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) throws CarNotFoundException, MaintenanceReturnDateBeforeTodayException, CarAlreadyInMaintenanceException, StartDateBeforeTodayException, CarAlreadyRentedEnteredDateException {
        return this.carMaintenanceService.add(createCarMaintenanceRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws CarNotFoundException, MaintenanceReturnDateBeforeTodayException, CarAlreadyInMaintenanceException, CarMaintenanceNotFoundException, StartDateBeforeTodayException, CarAlreadyRentedEnteredDateException {
        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws CarMaintenanceNotFoundException {
        return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
    }

    @GetMapping("/getByCarMaintenanceId")
    public DataResult<GetCarMaintenanceDto> getByCarMaintenanceId(@RequestParam int carMaintenanceId) throws CarMaintenanceNotFoundException {
        return this.carMaintenanceService.getById(carMaintenanceId);
    }

    @GetMapping("/getAllByCarMaintenance_CarId")
    public DataResult<List<CarMaintenanceListByCarIdDto>> getAllByCarMaintenance_CarId(@RequestParam int carId) throws CarNotFoundException {
        return this.carMaintenanceService.getAllByCarMaintenance_CarId(carId);
    }

}
