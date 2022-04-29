package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.gets.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.lists.IndividualCustomerListDto;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.creditCardExceptions.CreditCardAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.individualCustomerExceptions.IndividualCustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.individualCustomerExceptions.NationalIdentityAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions.CustomerNotFoundInInvoiceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.CustomerAlreadyExistsInRentalCarException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserEmailNotValidException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

    private final IndividualCustomerService individualCustomerService;

    @Autowired
    public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
        this.individualCustomerService = individualCustomerService;
    }


    @GetMapping("/getAll")
    public DataResult<List<IndividualCustomerListDto>> getAll(){
        return this.individualCustomerService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NationalIdentityAlreadyExistsException, UserAlreadyExistsException {
        return this.individualCustomerService.add(createIndividualCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws NationalIdentityAlreadyExistsException, IndividualCustomerNotFoundException, UserEmailNotValidException {
        return this.individualCustomerService.update(updateIndividualCustomerRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws CreditCardAlreadyExistsException, IndividualCustomerNotFoundException, CustomerAlreadyExistsInRentalCarException, CustomerNotFoundInInvoiceException {
        return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetIndividualCustomerDto> getById(@RequestParam int individualCustomerId) throws IndividualCustomerNotFoundException {
        return this.individualCustomerService.getById(individualCustomerId);
    }

}
