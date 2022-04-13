package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.customerDtos.lists.CustomerListDto;
import com.turkcell.rentACarProject.business.dtos.customerDtos.gets.GetCustomerDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.entities.concretes.Customer;

import java.util.List;

public interface CustomerService {

    DataResult<List<CustomerListDto>> getAll();
    DataResult<GetCustomerDto> getById(int customerId) throws BusinessException;

    void checkIfCustomerIdExists(int customerId) throws BusinessException;

    Customer getCustomerById(int customerId);

}

