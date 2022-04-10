package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CarCrashService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.gets.carCrash.GetCarCrashDto;
import com.turkcell.rentACarProject.business.dtos.lists.carCrash.CarCrashListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCarCrashRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarCrashRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarCrashDao;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarCrash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarCrashManager implements CarCrashService {

    private CarCrashDao carCrashDao;
    private CarService carService;
    private ModelMapperService modelMapperService;

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
    public DataResult<List<CarCrashListDto>> getCarCrashByCar_CarId(int carId) throws BusinessException {

        checkIfExistsCarCrashByCar_CarId(carId);

        List<CarCrash> carCrashes = this.carCrashDao.getAllByCar_CarId(carId);

        List<CarCrashListDto> result = carCrashes.stream().map(carCrash -> this.modelMapperService.forDto()
                .map(carCrash, CarCrashListDto.class)).collect(Collectors.toList());
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

    private void checkIfExistsCarCrashByCar_CarId(int carId) throws BusinessException {
        if(!this.carCrashDao.existsByCar_CarId(carId)){
            throw new BusinessException("Car id not found in the Car Crash table, carId: " + carId);
        }
    }

    @Override
    public void checkIfNotExistsCarCrashByCar_CarId(int carId) throws BusinessException {
        if(this.carCrashDao.existsByCar_CarId(carId)){
            throw new BusinessException("Car id already exists in the Car Crash table, carId: " + carId);
        }
    }
}