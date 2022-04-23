package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.carCrashDtos.gets.GetCarCrashDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListByCarIdDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListDto;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.CreateCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.DeleteCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.UpdateCarCrashRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions.CarCrashNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions.CarExistsInCarCrashException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions.CrashDateAfterTodayException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.CarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CarCrashService {

    DataResult<List<CarCrashListDto>> getAll();

    Result add(CreateCarCrashRequest createCarCrashRequest) throws CrashDateAfterTodayException, CarNotFoundException;
    Result update(UpdateCarCrashRequest updateCarCrashRequest) throws CrashDateAfterTodayException, CarCrashNotFoundException, CarNotFoundException;
    Result delete(DeleteCarCrashRequest deleteCarCrashRequest) throws CarCrashNotFoundException;

    DataResult<GetCarCrashDto> getById(int carCrashId) throws CarCrashNotFoundException;
    DataResult<List<CarCrashListByCarIdDto>> getCarCrashByCar_CarId(int carId) throws CarNotFoundException;

    void checkIfNotExistsCarCrashByCar_CarId(int carId) throws CarExistsInCarCrashException;

}
