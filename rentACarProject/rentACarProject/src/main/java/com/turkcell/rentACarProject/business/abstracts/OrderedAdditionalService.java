package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.GetOrderedAdditionalDto;
import com.turkcell.rentACarProject.business.dtos.OrderedAdditionalListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;

import java.util.List;

public interface OrderedAdditionalService {

    DataResult<List<OrderedAdditionalListDto>> getAll();

    Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException;
    Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException;
    Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException;

    DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException;
    DataResult<List<OrderedAdditionalListDto>> getByOrderedAdditional_RentalCarId(int rentalCarId) throws BusinessException;
    DataResult<List<OrderedAdditionalListDto>> getByOrderedAdditional_AdditionalId(int additionalId) throws BusinessException;
    OrderedAdditional getById(int orderedAdditionalId);

    void checkIsExistsByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException;
    void checkIsNotExistsByOrderedAdditional_RentalCarId(int rentalCarId) throws BusinessException;
    void checkIsNotExistsByOrderedAdditional_AdditionalId(int additionalId) throws BusinessException;
    void checkIsOnlyOneOrderedAdditionalByAdditionalIdAndRentalCarIdForUpdate(int additionalId, int rentalCarId) throws BusinessException;
    void checkAllValidationForAddOrderedAdditional(List<CreateOrderedAdditionalRequest> orderedAdditionalRequestList) throws BusinessException;
    void checkAllValidationForAddOrderedAdditional(int additionalId, int orderedAdditionalQuantity) throws BusinessException;

    double getPriceCalculatorForAdditional(int additionalId,double orderedAdditionalQuantity, int totalDays) throws BusinessException;
    double calculateTotalPriceForOrderedAdditionals(int rentalCarId, int totalDays) throws BusinessException;
    double calculateTotalPriceForOrderedAdditionals(List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList, int totalDays) throws BusinessException;

}
