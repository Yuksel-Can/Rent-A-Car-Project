package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACarProject.business.dtos.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.time.LocalDate;
import java.util.List;

public interface CarMaintenanceService {

	DataResult<List<CarMaintenanceListDto>> getAll();

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);
	Result delete(DeleteCarMaintenanceRequest carMaintenanceRequest);

	DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId);
	void checkIfReturnDateBeforeToday(LocalDate returnDate) throws BusinessException;
	
}
