package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.corporateCustomerDtos.gets.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.corporateCustomerDtos.lists.CorporateCustomerListDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;

import java.util.List;

public interface CorporateCustomerService {

    DataResult<List<CorporateCustomerListDto>> getAll();

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;
    Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;
    Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException;

    DataResult<GetCorporateCustomerDto> getById(int corporateCustomerId) throws BusinessException;
    CorporateCustomer getCorporateCustomerById(int corporateCustomerId);
    void checkIfCorporateCustomerIdExists(int corporateCustomerId) throws BusinessException;
}
