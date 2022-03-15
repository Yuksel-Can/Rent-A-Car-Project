package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.dtos.CityListDto;
import com.turkcell.rentACarProject.business.dtos.GetCityDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCityRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCityRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
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

    private CityDao cityDao;
    private ModelMapperService modelMapperService;

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
    public Result add(CreateCityRequest createCityRequest) throws BusinessException {

        checkIfNotExistsByCityName(createCityRequest.getCityName());

        City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);

//      this.cityDao.save(city);

        return new SuccessResult("City added");

    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {

        checkIfExistsByCityId(updateCityRequest.getCityId());
        checkIfNotExistsByCityName(updateCityRequest.getCityName());

        City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);

//      this.cityDao.save(city);

        return new SuccessResult("City updated, cityId: " + updateCityRequest.getCityId());

    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException {

        checkIfExistsByCityId(deleteCityRequest.getCityId());

//        this.cityDao.deleteById(deleteCityRequest.getCityId());

        return new SuccessResult("City deleted, cityId: " + deleteCityRequest.getCityId());

    }

    @Override
    public DataResult<GetCityDto> getByCityId(int cityId) throws BusinessException {

        checkIfExistsByCityId(cityId);

        City city = this.cityDao.getById(cityId);

        GetCityDto result = this.modelMapperService.forDto().map(city, GetCityDto.class);

        return new SuccessDataResult<>(result, "City listed by cityId: " + cityId);

    }

    private void checkIfExistsByCityId(int cityId) throws BusinessException {
        if(!this.cityDao.existsByCityId(cityId)){
            throw new BusinessException("City id not exists");
        }
    }

    private void checkIfNotExistsByCityName(String cityName) throws BusinessException {
        if(this.cityDao.existsByCityName(cityName)){
            throw new BusinessException("City name already exists");
        }
    }


}
