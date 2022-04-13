package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CarCrashService;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.gets.GetCarCrashDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListDto;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.CreateCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.DeleteCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.UpdateCarCrashRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carCrashes")
public class CarCrashesController {

    private CarCrashService carCrashService;

    @Autowired
    public CarCrashesController(CarCrashService carCrashService) {
        this.carCrashService = carCrashService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CarCrashListDto>> getAll(){
        return this.carCrashService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarCrashRequest createCarCrashRequest) throws BusinessException {
        return this.carCrashService.add(createCarCrashRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarCrashRequest updateCarCrashRequest) throws BusinessException {
        return this.carCrashService.update(updateCarCrashRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteCarCrashRequest deleteCarCrashRequest) throws BusinessException {
        return this.carCrashService.delete(deleteCarCrashRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetCarCrashDto> getById(@RequestParam int carCrashId) throws BusinessException {
        return this.carCrashService.getById(carCrashId);
    }

    @GetMapping("/getCarCrashByCar_CarId")
    public DataResult<List<CarCrashListDto>> getCarCrashByCar_CarId(@RequestParam int carId) throws BusinessException {
        return this.carCrashService.getCarCrashByCar_CarId(carId);
    }
}
