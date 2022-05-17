package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.business.dtos.cityDtos.lists.CityListDto;
import com.turkcell.rentACarProject.business.dtos.cityDtos.gets.GetCityDto;
import com.turkcell.rentACarProject.business.requests.citiesRequests.CreateCityRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions.CityAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions.CityNotFoundException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
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

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) throws CityAlreadyExistsException {

        checkIsNotExistByCityName(createCityRequest.getCityName());

        City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
        city.setCityId(0);

        this.cityDao.save(city);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<GetCityDto> getByCityId(int cityId) throws CityNotFoundException {

        checkIfExistsByCityId(cityId);

        City city = this.cityDao.getById(cityId);

        GetCityDto result = this.modelMapperService.forDto().map(city, GetCityDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + cityId);
    }

    public void checkIfExistsByCityId(int cityId) throws CityNotFoundException {
        if(!this.cityDao.existsByCityId(cityId)){
            throw new CityNotFoundException(BusinessMessages.CityMessages.CITY_ID_NOT_FOUND + cityId);
        }
    }

    private void checkIsNotExistByCityName(String cityName) throws CityAlreadyExistsException {
        if(this.cityDao.existsByCityName(cityName)) {
            throw new CityAlreadyExistsException(BusinessMessages.CityMessages.CITY_NAME_ALREADY_EXISTS + cityName);
        }
    }

}
