package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACarProject.business.dtos.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carmaintenances")
public class CarMaintenancesController {

    private CarMaintenanceService carMaintenanceService;

    @Autowired
    public CarMaintenancesController(CarMaintenanceService carMaintenanceService){
        this.carMaintenanceService = carMaintenanceService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CarMaintenanceListDto>> getall() {
        return this.carMaintenanceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.add(createCarMaintenanceRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
    }

    @GetMapping("/getByCarMaintenanceId")
    public DataResult<GetCarMaintenanceDto> getByCarMaintenanceId(@RequestParam int carMaintenanceId) throws BusinessException {
        return this.carMaintenanceService.getByCarMaintenanceId(carMaintenanceId);
    }

    @GetMapping("/getAllByCarMaintenance_CarId")
    public DataResult<List<CarMaintenanceListDto>> getAllByCarMaintenance_CarId(@RequestParam int carId) throws BusinessException {
        return this.carMaintenanceService.getAllByCarMaintenance_CarId(carId);
    }

}
