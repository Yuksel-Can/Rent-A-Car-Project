package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACarProject.business.dtos.corporateCustomerDtos.gets.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.corporateCustomerDtos.lists.CorporateCustomerListDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.corporateCustomerExceptions.TaxNumberAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.creditCardExceptions.CreditCardAlreadyExistsException;
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
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {

    private final CorporateCustomerService corporateCustomerService;

    @Autowired
    public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
        this.corporateCustomerService = corporateCustomerService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CorporateCustomerListDto>> getAll(){
        return this.corporateCustomerService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) throws TaxNumberAlreadyExistsException, UserAlreadyExistsException {
        return this.corporateCustomerService.add(createCorporateCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws CorporateCustomerNotFoundException, TaxNumberAlreadyExistsException, UserEmailNotValidException {
        return this.corporateCustomerService.update(updateCorporateCustomerRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws CorporateCustomerNotFoundException, CreditCardAlreadyExistsException, CustomerAlreadyExistsInRentalCarException, CustomerNotFoundInInvoiceException {
        return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetCorporateCustomerDto> getById(@RequestParam int corporateCustomerId) throws CorporateCustomerNotFoundException {
        return this.corporateCustomerService.getById(corporateCustomerId);
    }

}
