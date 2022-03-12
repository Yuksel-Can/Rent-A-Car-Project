package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACarProject.business.dtos.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.List;

public interface CarMaintenanceService {

	DataResult<List<CarMaintenanceListDto>> getAll();

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
	Result delete(DeleteCarMaintenanceRequest carMaintenanceRequest) throws BusinessException;

	DataResult<GetCarMaintenanceDto> getByCarMaintenanceId(int carMaintenanceId) throws BusinessException;
	DataResult<List<CarMaintenanceListDto>> getAllByCarMaintenance_CarId(int carId) throws BusinessException;

	void checkIsExistsByCarMaintenanceId(int carMaintenanceId) throws BusinessException;
	void checkIsExistsByCarMaintenance_CarId(int carId) throws BusinessException;
	void checkNotReturnDateBeforeToday(LocalDate returnDate) throws BusinessException;
}
