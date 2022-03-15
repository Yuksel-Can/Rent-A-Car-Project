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

    @PostMapping("/add")
    public Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException {
        return this.orderedAdditionalService.add(createOrderedAdditionalRequest);
    }

    @PutMapping("/update")
    public Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest)throws BusinessException {
        return this.orderedAdditionalService.update(updateOrderedAdditionalRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws  BusinessException {
        return this.orderedAdditionalService.delete(deleteOrderedAdditionalRequest);
    }

    @GetMapping("getByOrderedAdditionalId")
    public DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException {
        return this.orderedAdditionalService.getByOrderedAdditionalId(orderedAdditionalId);
    }

}