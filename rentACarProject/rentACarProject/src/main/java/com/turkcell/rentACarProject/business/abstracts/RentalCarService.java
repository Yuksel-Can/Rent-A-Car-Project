package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.RentalCarListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateRentalCarRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface RentalCarService {

    DataResult<List<RentalCarListDto>> getAll();

    Result add(CreateRentalCarRequest createRentalCarRequest);
    Result update(UpdateRentalCarRequest updateRentalCarRequest);
    Result delete(DeleteRentalCarRequest deleteRentalCarRequest);

    DataResult<GetRentalCarDto> getById(int rentalCarId);
}
