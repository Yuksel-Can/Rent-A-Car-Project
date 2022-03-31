package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.gets.corporateCustomer.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.lists.corporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CorporateCustomerService {

    DataResult<List<CorporateCustomerListDto>> getAll();

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;
    Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;
    Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException;

    DataResult<GetCorporateCustomerDto> getById(int corporateCustomerId) throws BusinessException;

    void checkIfCorporateCustomerIdExists(int corporateCustomerId) throws BusinessException;
}
