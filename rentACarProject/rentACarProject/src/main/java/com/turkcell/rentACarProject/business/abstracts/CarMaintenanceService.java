package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.lists.CarMaintenanceListDto;
import com.turkcell.rentACarProject.business.dtos.carMaintenanceDtos.gets.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.time.LocalDate;
import java.util.List;

public interface CarMaintenanceService {

	DataResult<List<CarMaintenanceListDto>> getAll();

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
	Result delete(DeleteCarMaintenanceRequest carMaintenanceRequest) throws BusinessException;

	DataResult<GetCarMaintenanceDto> getByCarMaintenanceId(int carMaintenanceId) throws BusinessException;
	DataResult<List<CarMaintenanceListDto>> getAllByCarMaintenance_CarId(int carId) throws BusinessException;

	void checkIfNotReturnDateBeforeToday(LocalDate returnDate) throws BusinessException;
	void checkIfNotCarAlreadyInMaintenanceOnTheEnteredDate(int carId, LocalDate enteredDate) throws BusinessException;
	void checkIsExistsByCarMaintenanceId(int carMaintenanceId) throws BusinessException;
	void checkIsExistsByCarMaintenance_CarId(int carId) throws BusinessException;

}
