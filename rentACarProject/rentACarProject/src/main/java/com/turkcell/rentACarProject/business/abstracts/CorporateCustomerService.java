package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.gets.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.lists.CorporateCustomerListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CorporateCustomerService {

    DataResult<List<CorporateCustomerListDto>> getAll();

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);
    Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);
    Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);

    DataResult<GetCorporateCustomerDto> getById(int corporateCustomerId);
}
