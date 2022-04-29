package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.business.dtos.customerDtos.lists.CustomerListDto;
import com.turkcell.rentACarProject.business.dtos.customerDtos.gets.GetCustomerDto;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.customerExceptions.CustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    private final CustomerService customerService;

    @Autowired
    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CustomerListDto>> getAll(){
        return this.customerService.getAll();
    }

    @GetMapping("getById")
    public DataResult<GetCustomerDto> getById(@RequestParam int customerId) throws CustomerNotFoundException {
        return this.customerService.getById(customerId);
    }

}
