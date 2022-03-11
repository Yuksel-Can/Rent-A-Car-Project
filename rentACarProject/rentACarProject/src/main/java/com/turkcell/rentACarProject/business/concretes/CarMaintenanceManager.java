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

        this.carService.checkIsExistsByCarId(createCarMaintenanceRequest.getCarId());
        checkIfReturnDateBeforeToday(createCarMaintenanceRequest.getReturnDate());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("CarMaintenance Added");

    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
        return null;
    }

    @Override
    public Result delete(DeleteCarMaintenanceRequest carMaintenanceRequest) {
        return null;
    }

    @Override
    public DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId) {
        return null;
    }

    @Override
    public void checkIfReturnDateBeforeToday(LocalDate returnDate) throws BusinessException {

        if(returnDate.isBefore(LocalDate.now())){
            throw new BusinessException("return date cannot be a past date");
        }
    }
}
