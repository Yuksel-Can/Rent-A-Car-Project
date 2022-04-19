package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CarCrashService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.gets.GetCarCrashDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListByCarIdDto;
import com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists.CarCrashListDto;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.CreateCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.DeleteCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.carCrashRequests.UpdateCarCrashRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
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

        return new SuccessDataResult<>(result, "Car Crash listed");
    }

    @Override
    public Result add(CreateCarCrashRequest createCarCrashRequest) throws BusinessException {

        checkIfCrashDateBeforeToday(createCarCrashRequest.getCrashDate());
        this.carService.checkIsExistsByCarId(createCarCrashRequest.getCarId());

        CarCrash carCrash = this.modelMapperService.forRequest().map(createCarCrashRequest, CarCrash.class);

        this.carCrashDao.save(carCrash);

        return new SuccessResult("Car Crash added");
    }

    @Override
    public Result update(UpdateCarCrashRequest updateCarCrashRequest) throws BusinessException {

        checkIfExistsByCarCrashId(updateCarCrashRequest.getCarCrashId());
        checkIfCrashDateBeforeToday(updateCarCrashRequest.getCrashDate());
        this.carService.checkIsExistsByCarId(updateCarCrashRequest.getCarId());

        CarCrash carCrash = this.modelMapperService.forRequest().map(updateCarCrashRequest, CarCrash.class);

        this.carCrashDao.save(carCrash);

        return new SuccessResult("Car Crash updated");
    }

    @Override
    public Result delete(DeleteCarCrashRequest deleteCarCrashRequest) throws BusinessException {

        checkIfExistsByCarCrashId(deleteCarCrashRequest.getCarCrashId());

        this.carCrashDao.deleteById(deleteCarCrashRequest.getCarCrashId());

        return new SuccessResult("Car Crash deleted");
    }

    @Override
    public DataResult<GetCarCrashDto> getById(int carCrashId) throws BusinessException {

        checkIfExistsByCarCrashId(carCrashId);

        CarCrash carCrash = this.carCrashDao.getById(carCrashId);

        GetCarCrashDto result = this.modelMapperService.forDto().map(carCrash, GetCarCrashDto.class);
        result.setCarId(carCrash.getCar().getCarId());

        return new SuccessDataResult<>(result, "Car Crash listed");
    }

    @Override
    public DataResult<List<CarCrashListByCarIdDto>> getCarCrashByCar_CarId(int carId) throws BusinessException {

        this.carService.checkIsExistsByCarId(carId);

        List<CarCrash> carCrashes = this.carCrashDao.getAllByCar_CarId(carId);

        List<CarCrashListByCarIdDto> result = carCrashes.stream().map(carCrash -> this.modelMapperService.forDto()
                .map(carCrash, CarCrashListByCarIdDto.class)).collect(Collectors.toList());
        for(int i=0;i<carCrashes.size();i++){
            result.get(i).setCarId(carCrashes.get(i).getCar().getCarId());
        }

        return new SuccessDataResult<>(result, "Car Crash listed by car id, carId: " + carId);
    }

    private void checkIfExistsByCarCrashId(int carCrashId) throws BusinessException {
        if(!this.carCrashDao.existsByCarCrashId(carCrashId)){
            throw new BusinessException("Car crash id not found, carCrashId: " + carCrashId);
        }
    }

    private void checkIfCrashDateBeforeToday(LocalDate crashDate) throws BusinessException {
        if(crashDate.isAfter(LocalDate.now())){
            throw new BusinessException("Crash date cannot be after today, crashDate: " + crashDate);
        }
    }

    @Override
    public void checkIfNotExistsCarCrashByCar_CarId(int carId) throws BusinessException {
        if(this.carCrashDao.existsByCar_CarId(carId)){
            throw new BusinessException("Car id already exists in the Car Crash table, carId: " + carId);
        }
    }
}
