package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.cityDtos.lists.CityListDto;
import com.turkcell.rentACarProject.business.dtos.cityDtos.gets.GetCityDto;
import com.turkcell.rentACarProject.business.requests.citiesRequests.CreateCityRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions.CityAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions.CityNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CityService {

    DataResult<List<CityListDto>> getAll();

    Result add(CreateCityRequest createCityRequest) throws CityAlreadyExistsException;

    DataResult<GetCityDto> getByCityId(int cityId) throws CityNotFoundException;

    void checkIfExistsByCityId(int cityId) throws CityNotFoundException;

}
