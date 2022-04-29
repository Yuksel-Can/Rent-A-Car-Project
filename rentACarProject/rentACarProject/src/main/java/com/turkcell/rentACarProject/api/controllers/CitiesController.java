package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.dtos.cityDtos.lists.CityListDto;
import com.turkcell.rentACarProject.business.dtos.cityDtos.gets.GetCityDto;
import com.turkcell.rentACarProject.business.requests.citiesRequests.CreateCityRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions.CityAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions.CityNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    private final CityService cityService;

    @Autowired
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CityListDto>> getAll(){
        return this.cityService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws CityAlreadyExistsException {
        return this.cityService.add(createCityRequest);
    }

    @GetMapping("getByCityId")
    public DataResult<GetCityDto> getByCityId(@RequestParam int cityId) throws CityNotFoundException {
        return this.cityService.getByCityId(cityId);
    }

}
