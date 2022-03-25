package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.business.dtos.CustomerListDto;
import com.turkcell.rentACarProject.business.dtos.GetCustomerDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACarProject.entities.concretes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements CustomerService {

    private CustomerDao customerDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
        this.customerDao = customerDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<CustomerListDto>> getAll() {

        List<Customer> customers = this.customerDao.findAll();

        List<CustomerListDto> result = customers.stream().map(customer -> this.modelMapperService.forDto().map(customer, CustomerListDto.class))
                .collect(Collectors.toList());;

        return new SuccessDataResult<>(result, "Customer listed");

    }

    @Override
    public Result add(CreateCustomerRequest createCustomerRequest) {

        Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);

//        this.customerDao.save(customer);

        return new SuccessResult("Customer added");

    }

    @Override
    public Result update(UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = this.modelMapperService.forRequest().map(updateCustomerRequest, Customer.class);

//        this.customerDao.save(customer);

        return new SuccessResult("Customer updated");

    }

    @Override
    public Result delete(DeleteCustomerRequest deleteCustomerRequest) {

//        this.customerDao.deleteById(deleteCustomerRequest.getUserId());

        return new SuccessResult("Customer deleted");

    }

    @Override
    public DataResult<GetCustomerDto> getById(int customerId) {

        Customer customer = this.customerDao.getById(customerId);

        GetCustomerDto result = this.modelMapperService.forDto().map(customer, GetCustomerDto.class);

        return new SuccessDataResult<>(result, "Customer listed");

    }
}
