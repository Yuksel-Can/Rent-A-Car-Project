package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.dtos.CityListDto;
import com.turkcell.rentACarProject.business.dtos.GetCityDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCityRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCityRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    private CityService cityService;

    @Autowired
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CityListDto>> getAll(){
        return this.cityService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws BusinessException {
        return this.cityService.add(createCityRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest) throws BusinessException {
        return this.cityService.update(updateCityRequest);
    }

    @DeleteMapping("/delete")
    public Result delte(@RequestBody @Valid DeleteCityRequest deleteCityRequest) throws BusinessException {
        return this.cityService.delete(deleteCityRequest);
    }

    @GetMapping("getByCityId")
    public DataResult<GetCityDto> getByCityId(@RequestParam int cityId) throws BusinessException {
        return this.cityService.getByCityId(cityId);
    }


}
