package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.gets.GetOrderedAdditionalDto;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.lists.OrderedAdditionalListByAdditionalIdDto;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.lists.OrderedAdditionalListByRentalCarIdDto;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.lists.OrderedAdditionalListDto;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.additionalExceptions.AdditionalNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.orderedAdditionalExceptions.OrderedAdditionalNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.RentalCarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orderedAdditionals")
public class OrderedAdditionalsController {

    private final OrderedAdditionalService orderedAdditionalService;

    @Autowired
    public OrderedAdditionalsController(OrderedAdditionalService orderedAdditionalService) {
        this.orderedAdditionalService = orderedAdditionalService;
    }


    @GetMapping("/getAll")
    public DataResult<List<OrderedAdditionalListDto>> getAll(){
        return this.orderedAdditionalService.getAll();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws OrderedAdditionalNotFoundException {
        return this.orderedAdditionalService.delete(deleteOrderedAdditionalRequest);
    }

    @GetMapping("getByOrderedAdditionalId")
    public DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(@RequestParam int orderedAdditionalId) throws OrderedAdditionalNotFoundException {
        return this.orderedAdditionalService.getByOrderedAdditionalId(orderedAdditionalId);
    }

    @GetMapping("/getByOrderedAdditional_RentalCarId")
    public DataResult<List<OrderedAdditionalListByRentalCarIdDto>> getByOrderedAdditional_RentalCarId(@RequestParam int rentalCarId) throws RentalCarNotFoundException {
        return this.orderedAdditionalService.getByOrderedAdditional_RentalCarId(rentalCarId);
    }

    @GetMapping("/getByOrderedAdditional_AdditionalId")
    public DataResult<List<OrderedAdditionalListByAdditionalIdDto>> getByOrderedAdditional_AdditionalId(@RequestParam int additionalId) throws AdditionalNotFoundException {
        return this.orderedAdditionalService.getByOrderedAdditional_AdditionalId(additionalId);
    }

}