package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.CarListByDailyPrice;
import com.turkcell.rentACarProject.business.dtos.CarListDto;
import com.turkcell.rentACarProject.business.dtos.CarPagedDto;
import com.turkcell.rentACarProject.business.dtos.CarSortedDto;
import com.turkcell.rentACarProject.business.requests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface CarService {

	DataResult<List<CarListDto>> getAll();
	
	Result add(CreateCarRequest createCarRequest);
	Result update(UpdateCarRequest updateCarRequest);
	Result delete(int id);
	
	DataResult<GetCarDto> getById(int id);
	DataResult<List<CarListByDailyPrice>> findByDailyPriceLessThenEqual(double dailyPrice);
	DataResult<List<CarPagedDto>> getAllPagedCar(int pageNo, int pageSize);
	DataResult<List<CarSortedDto>> getAllSortedCar(int sort);
}
