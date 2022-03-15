package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.GetOrderedAdditionalDto;
import com.turkcell.rentACarProject.business.dtos.OrderedAdditionalListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface OrderedAdditionalService {

    DataResult<List<OrderedAdditionalListDto>> getAll();

    Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException;
    Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException;
    Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException;

    DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException;
}
