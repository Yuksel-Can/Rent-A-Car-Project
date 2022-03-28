package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.dtos.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

    private IndividualCustomerService individualCustomerService;

    @Autowired
    public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
        this.individualCustomerService = individualCustomerService;
    }

    @GetMapping("/getAll")
    public DataResult<List<IndividualCustomerListDto>> getAll(){
        return this.individualCustomerService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
        return this.individualCustomerService.add(createIndividualCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
        return this.individualCustomerService.update(updateIndividualCustomerRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
        return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetIndividualCustomerDto> getById(@RequestParam int individualCustomerId) throws BusinessException {
        return this.individualCustomerService.getById(individualCustomerId);
    }
}
