package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.RentalCarListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateRentalCarRequest;
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

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateRentalCarRequest createRentalCarRequest){
        return this.rentalCarService.add(createRentalCarRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateRentalCarRequest updateRentalCarRequest){
        return this.rentalCarService.update(updateRentalCarRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteRentalCarRequest deleteRentalCarRequest){
        return this.rentalCarService.delete(deleteRentalCarRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetRentalCarDto> getById(@RequestParam int rentalCarId){
        return this.rentalCarService.getById(rentalCarId);
    }
}