package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.dtos.CityListDto;
import com.turkcell.rentACarProject.business.dtos.GetCityDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CityDao;
import com.turkcell.rentACarProject.entities.concretes.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityManager implements CityService {

    private final CityDao cityDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
        this.cityDao = cityDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CityListDto>> getAll() {

        List<City> cities = this.cityDao.findAll();

        List<CityListDto> result = cities.stream().map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Cities listed");
    }

    @Override
    public DataResult<GetCityDto> getByCityId(int cityId) throws BusinessException {

        checkIfExistsByCityId(cityId);

        City city = this.cityDao.getById(cityId);

        GetCityDto result = this.modelMapperService.forDto().map(city, GetCityDto.class);

        return new SuccessDataResult<>(result, "City listed by cityId: " + cityId);

    }

    public void checkIfExistsByCityId(int cityId) throws BusinessException {
        if(!this.cityDao.existsByCityId(cityId)){
            throw new BusinessException("City id not exists");
        }
    }

}
