package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACarProject.business.dtos.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private CarMaintenanceDao carMaintenanceDao;
    private ModelMapperService modelMapperService;
    private CarService carService;

    @Autowired
    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, CarService carService){
        this.carMaintenanceDao = carMaintenanceDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
    }


    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> result = carMaintenances.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "CarMaintenance listed");

    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {

        checkIfNotReturnDateBeforeToday(createCarMaintenanceRequest.getReturnDate());
        this.carService.checkIsExistsByCarId(createCarMaintenanceRequest.getCarId());
        checkIfNotCarAlreadyInMaintenance(createCarMaintenanceRequest.getCarId());


        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);

        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("CarMaintenance added");

    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {

        checkIsExistsByCarMaintenanceId(updateCarMaintenanceRequest.getMaintenanceId());
        checkIfNotReturnDateBeforeToday(updateCarMaintenanceRequest.getReturnDate());
        this.carService.checkIsExistsByCarId(updateCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);

        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("CarMaintenance updated");

    }

    @Override
    public Result delete(DeleteCarMaintenanceRequest carMaintenanceRequest) throws BusinessException {

        checkIsExistsByCarMaintenanceId(carMaintenanceRequest.getMaintenanceId());

        this.carMaintenanceDao.deleteById(carMaintenanceRequest.getMaintenanceId());

        return new SuccessResult("CarMaintenance deleted");

    }

    @Override
    public DataResult<GetCarMaintenanceDto> getByCarMaintenanceId(int carMaintenanceId) throws BusinessException {

        checkIsExistsByCarMaintenanceId(carMaintenanceId);

        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);
        GetCarMaintenanceDto result = this.modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class);

        return new SuccessDataResult<>(result, "Car Maintenence getted by id: " + carMaintenanceId);

    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAllByCarMaintenance_CarId(int carId) throws BusinessException {

        checkIsExistsByCarMaintenance_CarId(carId);
        this.carService.checkIsExistsByCarId(carId);    //*

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findByCar_CarId(carId);

        List<CarMaintenanceListDto> result = carMaintenances.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Car maintenances are brought by car id: " + carId);

    }

    public void checkIsExistsByCarMaintenanceId(int carMaintenanceId) throws BusinessException {

        if(!this.carMaintenanceDao.existsByMaintenanceId(carMaintenanceId)){
            throw new BusinessException("Car Maintenance id not exists");
        }
    }

    public void checkIsExistsByCarMaintenance_CarId(int carId) throws BusinessException {

        if(!this.carMaintenanceDao.existsByCar_CarId(carId)){
            throw new BusinessException("Car id not found in car maintenance");
        }
    }

    @Override
    public void checkIfNotReturnDateBeforeToday(LocalDate returnDate) throws BusinessException {

        if(returnDate != null){
            if(returnDate.isBefore(LocalDate.now())){
                throw new BusinessException("return date cannot be a past date");
            }
        }
    }

    @Override
    public void checkIfNotCarAlreadyInMaintenance(int carId) throws BusinessException{

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findByCar_CarId(carId);

        for(CarMaintenance carMaintenance : carMaintenances){
            if((carMaintenance.getReturnDate()==null ) || (carMaintenance.getReturnDate().isAfter(LocalDate.now()))){
                throw new BusinessException("Car already in maintenance, id: " + carId);
            }
        }
    }



}
