package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CarCrashService;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.gets.GetCarCrashDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListByCarIdDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListDto;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.CreateCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.DeleteCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.UpdateCarCrashRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions.CarCrashNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions.CrashDateAfterTodayException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.CarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carCrashes")
public class CarCrashesController {

    private final CarCrashService carCrashService;

    @Autowired
    public CarCrashesController(CarCrashService carCrashService) {
        this.carCrashService = carCrashService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CarCrashListDto>> getAll(){
        return this.carCrashService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarCrashRequest createCarCrashRequest) throws CrashDateAfterTodayException, CarNotFoundException {
        return this.carCrashService.add(createCarCrashRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarCrashRequest updateCarCrashRequest) throws CrashDateAfterTodayException, CarCrashNotFoundException, CarNotFoundException {
        return this.carCrashService.update(updateCarCrashRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteCarCrashRequest deleteCarCrashRequest) throws CarCrashNotFoundException {
        return this.carCrashService.delete(deleteCarCrashRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetCarCrashDto> getById(@RequestParam int carCrashId) throws CarCrashNotFoundException {
        return this.carCrashService.getById(carCrashId);
    }

    @GetMapping("/getCarCrashByCar_CarId")
    public DataResult<List<CarCrashListByCarIdDto>> getCarCrashByCar_CarId(@RequestParam int carId) throws CarNotFoundException {
        return this.carCrashService.getCarCrashByCar_CarId(carId);
    }

}
