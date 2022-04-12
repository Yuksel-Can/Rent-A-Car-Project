package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.CityListDto;
import com.turkcell.rentACarProject.business.dtos.GetCityDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;

import java.util.List;

public interface CityService {

    DataResult<List<CityListDto>> getAll();

    DataResult<GetCityDto> getByCityId(int cityId) throws BusinessException;

    void checkIfExistsByCityId(int cityId) throws BusinessException;
}
