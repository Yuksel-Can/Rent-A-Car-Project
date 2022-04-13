package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.business.dtos.customerDtos.lists.CustomerListDto;
import com.turkcell.rentACarProject.business.dtos.customerDtos.gets.GetCustomerDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACarProject.entities.concretes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements CustomerService {

    private final CustomerDao customerDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
        this.customerDao = customerDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<CustomerListDto>> getAll() {

        List<Customer> customers = this.customerDao.findAll();

        List<CustomerListDto> result = customers.stream().map(customer -> this.modelMapperService.forDto().map(customer, CustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Customer listed");

    }

    @Override
    public DataResult<GetCustomerDto> getById(int customerId) throws BusinessException {

        checkIfCustomerIdExists(customerId);

        Customer customer = this.customerDao.getById(customerId);

        GetCustomerDto result = this.modelMapperService.forDto().map(customer, GetCustomerDto.class);

        return new SuccessDataResult<>(result, "Customer listed");

    }

    @Override
    public void checkIfCustomerIdExists(int customerId) throws BusinessException {
        if(!this.customerDao.existsByCustomerId(customerId)){
            throw new BusinessException("Customer id not found, customerId: " + customerId);
        }
    }

    @Override
    public Customer getCustomerById(int customerId){
        return this.customerDao.getById(customerId);
    }
}
