package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.CarListByDailyPrice;
import com.turkcell.rentACarProject.business.dtos.CarListDto;
import com.turkcell.rentACarProject.business.dtos.CarPagedDto;
import com.turkcell.rentACarProject.business.dtos.CarSortedDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface CarService {

	DataResult<List<CarListDto>> getAll();
	
	Result add(CreateCarRequest createCarRequest) throws BusinessException;
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;

	DataResult<List<CarListByDailyPrice>> findByDailyPriceLessThenEqual(double dailyPrice);
	DataResult<List<CarPagedDto>> getAllPagedCar(int pageNo, int pageSize);
	DataResult<List<CarSortedDto>> getAllSortedCar(int sort);
	DataResult<GetCarDto> getById(int id) throws BusinessException;
	DataResult<List<CarListDto>> getAllByCar_BrandId(int brandId) throws BusinessException;
	DataResult<List<CarListDto>> getAllByCar_ColorId(int colorId) throws BusinessException;

	void checkIsExistsByCarId(int carId) throws  BusinessException;
	void checkIsNotExistsByCar_BrandId(int brandId) throws BusinessException;
	void checkIsNotExistsByCar_ColorId(int colorId) throws BusinessException;

	double getDailyPriceByCarId(int carId);
}
