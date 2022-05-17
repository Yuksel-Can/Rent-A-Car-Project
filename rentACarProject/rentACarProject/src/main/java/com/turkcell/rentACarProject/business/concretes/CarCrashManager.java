package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CarCrashService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
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
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarCrashDao;
import com.turkcell.rentACarProject.entities.concretes.CarCrash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarCrashManager implements CarCrashService {

    private final CarCrashDao carCrashDao;
    private final CarService carService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CarCrashManager(CarCrashDao carCrashDao, CarService carService, ModelMapperService modelMapperService) {
        this.carCrashDao = carCrashDao;
        this.carService = carService;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<CarCrashListDto>> getAll() {

        List<CarCrash> carCrashes = this.carCrashDao.findAll();

        List<CarCrashListDto> result = carCrashes.stream().map(carCrash -> this.modelMapperService.forDto().map(carCrash, CarCrashListDto.class))
                .collect(Collectors.toList());
        for(int i=0;i<carCrashes.size();i++){
            result.get(i).setCarId(carCrashes.get(i).getCar().getCarId());
        }

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCarCrashRequest createCarCrashRequest) throws CrashDateAfterTodayException, CarNotFoundException {

        checkIfCrashDateBeforeToday(createCarCrashRequest.getCrashDate());
        this.carService.checkIsExistsByCarId(createCarCrashRequest.getCarId());

        CarCrash carCrash = this.modelMapperService.forRequest().map(createCarCrashRequest, CarCrash.class);

        this.carCrashDao.save(carCrash);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateCarCrashRequest updateCarCrashRequest) throws CrashDateAfterTodayException, CarCrashNotFoundException, CarNotFoundException {

        checkIfExistsByCarCrashId(updateCarCrashRequest.getCarCrashId());
        checkIfCrashDateBeforeToday(updateCarCrashRequest.getCrashDate());
        this.carService.checkIsExistsByCarId(updateCarCrashRequest.getCarId());

        CarCrash carCrash = this.modelMapperService.forRequest().map(updateCarCrashRequest, CarCrash.class);

        this.carCrashDao.save(carCrash);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateCarCrashRequest.getCarCrashId());
    }

    @Override
    public Result delete(DeleteCarCrashRequest deleteCarCrashRequest) throws CarCrashNotFoundException {

        checkIfExistsByCarCrashId(deleteCarCrashRequest.getCarCrashId());

        this.carCrashDao.deleteById(deleteCarCrashRequest.getCarCrashId());

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + deleteCarCrashRequest.getCarCrashId());
    }

    @Override
    public DataResult<GetCarCrashDto> getById(int carCrashId) throws CarCrashNotFoundException {

        checkIfExistsByCarCrashId(carCrashId);

        CarCrash carCrash = this.carCrashDao.getById(carCrashId);

        GetCarCrashDto result = this.modelMapperService.forDto().map(carCrash, GetCarCrashDto.class);
        result.setCarId(carCrash.getCar().getCarId());

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + carCrashId);
    }

    @Override
    public DataResult<List<CarCrashListByCarIdDto>> getCarCrashByCar_CarId(int carId) throws CarNotFoundException {

        this.carService.checkIsExistsByCarId(carId);

        List<CarCrash> carCrashes = this.carCrashDao.getAllByCar_CarId(carId);

        List<CarCrashListByCarIdDto> result = carCrashes.stream().map(carCrash -> this.modelMapperService.forDto()
                .map(carCrash, CarCrashListByCarIdDto.class)).collect(Collectors.toList());
        for(int i=0;i<carCrashes.size();i++){
            result.get(i).setCarId(carCrashes.get(i).getCar().getCarId());
        }

        return new SuccessDataResult<>(result, BusinessMessages.CarCrashMessages.CAR_CRASH_LISTED_BY_CAR_ID + carId);
    }

    private void checkIfExistsByCarCrashId(int carCrashId) throws CarCrashNotFoundException {
        if(!this.carCrashDao.existsByCarCrashId(carCrashId)){
            throw new CarCrashNotFoundException(BusinessMessages.CarCrashMessages.CAR_CRASH_ID_NOT_FOUND + carCrashId);
        }
    }

    private void checkIfCrashDateBeforeToday(LocalDate crashDate) throws CrashDateAfterTodayException {
        if(crashDate.isAfter(LocalDate.now())){
            throw new CrashDateAfterTodayException(BusinessMessages.CarCrashMessages.CRASH_DATE_CANNOT_AFTER_TODAY + crashDate);
        }
    }

    @Override
    public void checkIfNotExistsCarCrashByCar_CarId(int carId) throws CarExistsInCarCrashException {
        if(this.carCrashDao.existsByCar_CarId(carId)){
            throw new CarExistsInCarCrashException(BusinessMessages.CarCrashMessages.CAR_ID_ALREADY_EXISTS_IN_THE_CAR_CRASH_TABLE + carId);
        }
    }
}
