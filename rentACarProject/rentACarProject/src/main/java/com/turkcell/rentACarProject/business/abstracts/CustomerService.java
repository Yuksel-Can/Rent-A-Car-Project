package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.customerDtos.lists.CustomerListDto;
import com.turkcell.rentACarProject.business.dtos.customerDtos.gets.GetCustomerDto;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.customerExceptions.CustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.entities.concretes.Customer;

import java.util.List;

public interface CustomerService {

    DataResult<List<CustomerListDto>> getAll();
    DataResult<GetCustomerDto> getById(int customerId) throws CustomerNotFoundException;

    void checkIfCustomerIdExists(int customerId) throws CustomerNotFoundException;

    Customer getCustomerById(int customerId);

}

