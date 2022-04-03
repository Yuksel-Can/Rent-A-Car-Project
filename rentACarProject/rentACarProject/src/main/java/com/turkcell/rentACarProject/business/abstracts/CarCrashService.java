package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.gets.carCrash.GetCarCrashDto;
import com.turkcell.rentACarProject.business.dtos.lists.carCrash.CarCrashListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarCrashRequest;
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
    DataResult<List<CarCrashListDto>> getCarCrashByCar_CarId(int carId) throws BusinessException;

    void checkIfNotExistsCarCrashByCar_CarId(int carId) throws BusinessException;

}
