package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.carCrashDtos.gets.GetCarCrashDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListByCarIdDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListDto;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.CreateCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.DeleteCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.UpdateCarCrashRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CarCrashService {

    DataResult<List<CarCrashListDto>> getAll();

    Result add(CreateCarCrashRequest createCarCrashRequest) throws BusinessException;
    Result update(UpdateCarCrashRequest updateCarCrashRequest) throws BusinessException;
    Result delete(DeleteCarCrashRequest deleteCarCrashRequest) throws BusinessException;

    DataResult<GetCarCrashDto> getById(int carCrashId) throws BusinessException;
    DataResult<List<CarCrashListByCarIdDto>> getCarCrashByCar_CarId(int carId) throws BusinessException;

    void checkIfNotExistsCarCrashByCar_CarId(int carId) throws BusinessException;

}
