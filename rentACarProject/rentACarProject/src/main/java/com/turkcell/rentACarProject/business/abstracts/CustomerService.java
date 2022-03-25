package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.CustomerListDto;
import com.turkcell.rentACarProject.business.dtos.GetCustomerDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CustomerService {

    DataResult<List<CustomerListDto>> getAll();

    Result add(CreateCustomerRequest createCustomerRequest);
    Result update(UpdateCustomerRequest updateCustomerRequest);
    Result delete(DeleteCustomerRequest deleteCustomerRequest);

    DataResult<GetCustomerDto> getById(int customerId);
}
