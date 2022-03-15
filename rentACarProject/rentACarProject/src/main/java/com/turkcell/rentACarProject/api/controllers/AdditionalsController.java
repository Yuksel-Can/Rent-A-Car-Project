package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.AdditionalService;
import com.turkcell.rentACarProject.business.dtos.AdditionalListDto;
import com.turkcell.rentACarProject.business.dtos.GetAdditionalDto;
import com.turkcell.rentACarProject.business.requests.create.CreateAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/additionals")
public class AdditionalsController {

    private AdditionalService additionalService;

    @Autowired
    public AdditionalsController(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }


    @GetMapping("/getAll")
    public DataResult<List<AdditionalListDto>> getAll(){
        return this.additionalService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateAdditionalRequest createAdditionalRequest) throws BusinessException {
        return this.additionalService.add(createAdditionalRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException {
        return this.additionalService.update(updateAdditionalRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException {
        return this.additionalService.delete(deleteAdditionalRequest);
    }

    @GetMapping("/getById")
    public DataResult<GetAdditionalDto> getById(@RequestParam int additionalId) throws BusinessException {
        return this.additionalService.getByAdditionalId(additionalId);
    }

}
