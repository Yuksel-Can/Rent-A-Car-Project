package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.CityListDto;
import com.turkcell.rentACarProject.business.dtos.GetCityDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCityRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCityRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CityService {

    DataResult<List<CityListDto>> getAll();

    Result add(CreateCityRequest createCityRequest) throws BusinessException;
    Result update(UpdateCityRequest updateCityRequest) throws BusinessException;
    Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException;

    DataResult<GetCityDto> getByCityId(int cityId) throws BusinessException;

    void checkIfExistsByCityId(int cityId) throws BusinessException;

}
