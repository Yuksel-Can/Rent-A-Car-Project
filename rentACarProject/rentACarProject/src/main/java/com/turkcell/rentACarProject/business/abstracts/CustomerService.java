package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.CustomerListDto;
import com.turkcell.rentACarProject.business.dtos.GetCustomerDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.entities.concretes.Customer;

import java.util.List;

public interface CustomerService {

    DataResult<List<CustomerListDto>> getAll();

    Result add(CreateCustomerRequest createCustomerRequest) throws BusinessException;
    Result update(UpdateCustomerRequest updateCustomerRequest) throws BusinessException;
    Result delete(DeleteCustomerRequest deleteCustomerRequest) throws BusinessException;

    DataResult<GetCustomerDto> getById(int customerId) throws BusinessException;

    void checkIfCustomerIdExists(int customerId) throws BusinessException;

    Customer getCustomerById(int customerId);

}

