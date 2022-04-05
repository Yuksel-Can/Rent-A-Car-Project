package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACarProject.business.dtos.GetOrderedAdditionalDto;
import com.turkcell.rentACarProject.business.dtos.OrderedAdditionalListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orderedAdditionals")
public class OrderedAdditionalsController {

    private OrderedAdditionalService orderedAdditionalService;

    @Autowired
    public OrderedAdditionalsController(OrderedAdditionalService orderedAdditionalService) {
        this.orderedAdditionalService = orderedAdditionalService;
    }


    @GetMapping("/getAll")
    public DataResult<List<OrderedAdditionalListDto>> getAll(){
        return this.orderedAdditionalService.getAll();
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest)throws BusinessException {
        return this.orderedAdditionalService.update(updateOrderedAdditionalRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws  BusinessException {
        return this.orderedAdditionalService.delete(deleteOrderedAdditionalRequest);
    }

    @GetMapping("getByOrderedAdditionalId")
    public DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(@RequestParam int orderedAdditionalId) throws BusinessException {
        return this.orderedAdditionalService.getByOrderedAdditionalId(orderedAdditionalId);
    }

    @GetMapping("/getByOrderedAdditional_RentalCarId")
    public DataResult<List<OrderedAdditionalListDto>> getByOrderedAdditional_RentalCarId(@RequestParam int rentalCarId) throws BusinessException {
        return this.orderedAdditionalService.getByOrderedAdditional_RentalCarId(rentalCarId);
    }

    @GetMapping("/getByOrderedAdditional_AdditionalId")
    public DataResult<List<OrderedAdditionalListDto>> getByOrderedAdditional_AdditionalId(@RequestParam int additionalId) throws BusinessException {
        return this.orderedAdditionalService.getByOrderedAdditional_AdditionalId(additionalId);
    }


}