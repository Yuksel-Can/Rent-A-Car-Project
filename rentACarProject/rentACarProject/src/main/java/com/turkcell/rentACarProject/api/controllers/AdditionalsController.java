package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.AdditionalService;
import com.turkcell.rentACarProject.business.dtos.additionalDtos.lists.AdditionalListDto;
import com.turkcell.rentACarProject.business.dtos.additionalDtos.gets.GetAdditionalDto;
import com.turkcell.rentACarProject.business.requests.additionalRequests.CreateAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.additionalRequests.DeleteAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.additionalRequests.UpdateAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.additionalExceptions.AdditionalAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.additionalExceptions.AdditionalNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.orderedAdditionalExceptions.AdditionalAlreadyExistsInOrderedAdditionalException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/additionals")
public class AdditionalsController {

    private final AdditionalService additionalService;

    @Autowired
    public AdditionalsController(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }


    @GetMapping("/getAll")
    public DataResult<List<AdditionalListDto>> getAll(){
        return this.additionalService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateAdditionalRequest createAdditionalRequest) throws AdditionalAlreadyExistsException {
        return this.additionalService.add(createAdditionalRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateAdditionalRequest updateAdditionalRequest) throws AdditionalNotFoundException, AdditionalAlreadyExistsException {
        return this.additionalService.update(updateAdditionalRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteAdditionalRequest deleteAdditionalRequest) throws AdditionalNotFoundException, AdditionalAlreadyExistsInOrderedAdditionalException {
        return this.additionalService.delete(deleteAdditionalRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetAdditionalDto> getById(@RequestParam int additionalId) throws AdditionalNotFoundException {
        return this.additionalService.getByAdditionalId(additionalId);
    }

}
