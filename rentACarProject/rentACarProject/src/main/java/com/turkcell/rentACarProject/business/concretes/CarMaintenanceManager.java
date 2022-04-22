package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.lists.CarMaintenanceListByCarIdDto;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.lists.CarMaintenanceListDto;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.gets.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
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

    private final CarMaintenanceDao carMaintenanceDao;
    private final CarService carService;
    private final RentalCarService rentalCarService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, CarService carService, RentalCarService rentalCarService, ModelMapperService modelMapperService){
        this.carMaintenanceDao = carMaintenanceDao;
        this.carService = carService;
        this.rentalCarService = rentalCarService;
        this.modelMapperService = modelMapperService;
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

        checkAllValidationForCarMaintenance(createCarMaintenanceRequest.getReturnDate(),createCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setMaintenanceId(0);

        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("CarMaintenance added");
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {

        checkIsExistsByCarMaintenanceId(updateCarMaintenanceRequest.getMaintenanceId());
        checkAllValidationForCarMaintenance(updateCarMaintenanceRequest.getReturnDate(), updateCarMaintenanceRequest.getCarId());

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
    public DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId) throws BusinessException {

        checkIsExistsByCarMaintenanceId(carMaintenanceId);

        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);
        GetCarMaintenanceDto result = this.modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class);

        return new SuccessDataResult<>(result, "Car Maintenence getted by id: " + carMaintenanceId);
    }

    @Override
    public DataResult<List<CarMaintenanceListByCarIdDto>> getAllByCarMaintenance_CarId(int carId) throws BusinessException {

        this.carService.checkIsExistsByCarId(carId);

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAllByCar_CarId(carId);

        List<CarMaintenanceListByCarIdDto> result = carMaintenances.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListByCarIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Car maintenances are listed by car id: " + carId);
    }

    private void checkAllValidationForCarMaintenance(LocalDate returnDate, int carId) throws BusinessException {

        checkIfNotReturnDateBeforeToday(returnDate);
        this.carService.checkIsExistsByCarId(carId);
        checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(carId, LocalDate.now());

        this.rentalCarService.checkIfNotCarAlreadyRentedEnteredDate(carId,LocalDate.now());
        this.rentalCarService.checkIfNotCarAlreadyRentedEnteredDate(carId,returnDate);
        this.rentalCarService.checkIfNotCarAlreadyRentedBetweenStartAndFinishDates(carId, LocalDate.now(), returnDate);
    }

    private void checkIfNotReturnDateBeforeToday(LocalDate returnDate) throws BusinessException {
        if(returnDate != null){
            if(returnDate.isBefore(LocalDate.now())){
                throw new BusinessException("return date cannot be a past date");
            }
        }
    }

    @Override
    public void checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(int carId, LocalDate enteredDate) throws BusinessException {

        List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAllByCar_CarId(carId);

        if(carMaintenances != null){
            for (CarMaintenance carMaintenance : carMaintenances){
                if (carMaintenance.getReturnDate()==null){
                    if(carMaintenance.getReturnDate().now().plusDays(14).isAfter(enteredDate)){         //todo:bu çalışmıyor olabilir, carMaintenance.getReturnDate() kısmını sil LocalDate.now() koy
                        throw new BusinessException("The car is in maintenance on the entered date");
                    }
                }else if(carMaintenance.getReturnDate().isAfter(enteredDate)){
                    throw new BusinessException("The car is in maintenance on the entered date");
                }
            }
        }
    }

    private void checkIsExistsByCarMaintenanceId(int carMaintenanceId) throws BusinessException {
        if(!this.carMaintenanceDao.existsByMaintenanceId(carMaintenanceId)){
            throw new BusinessException("Car Maintenance id not exists");
        }
    }

    @Override
    public void checkIsExistsByCar_CarId(int carId) throws BusinessException {
        if(!this.carMaintenanceDao.existsByCar_CarId(carId)){
            throw new BusinessException("Car id not found in car maintenance");
        }
    }

}
