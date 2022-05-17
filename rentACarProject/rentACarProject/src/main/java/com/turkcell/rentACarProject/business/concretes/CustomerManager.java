package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.business.dtos.customerDtos.lists.CustomerListDto;
import com.turkcell.rentACarProject.business.dtos.customerDtos.gets.GetCustomerDto;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.customerExceptions.CustomerNotFoundException;
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

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<GetCustomerDto> getById(int customerId) throws CustomerNotFoundException {

        checkIfCustomerIdExists(customerId);

        Customer customer = this.customerDao.getById(customerId);

        GetCustomerDto result = this.modelMapperService.forDto().map(customer, GetCustomerDto.class);

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + customerId);
    }

    @Override
    public void checkIfCustomerIdExists(int customerId) throws CustomerNotFoundException {
        if(!this.customerDao.existsByCustomerId(customerId)){
            throw new CustomerNotFoundException(BusinessMessages.CustomerMessages.CUSTOMER_ID_NOT_FOUND + customerId);
        }
    }

    @Override
    public Customer getCustomerById(int customerId){
        return this.customerDao.getById(customerId);
    }

}
