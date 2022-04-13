package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACarProject.business.dtos.corporateCustomerDtos.gets.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.corporateCustomerDtos.lists.CorporateCustomerListDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {

    private CorporateCustomerService corporateCustomerService;

    @Autowired
    public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
        this.corporateCustomerService = corporateCustomerService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CorporateCustomerListDto>> getAll(){
        return this.corporateCustomerService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
        return this.corporateCustomerService.add(createCorporateCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
        return this.corporateCustomerService.update(updateCorporateCustomerRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
        return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetCorporateCustomerDto> getById(@RequestParam int corporateCustomerId) throws BusinessException {
        return this.corporateCustomerService.getById(corporateCustomerId);
    }

}
