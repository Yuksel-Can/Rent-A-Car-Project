package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.gets.GetOrderedAdditionalDto;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.lists.OrderedAdditionalListByAdditionalIdDto;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.lists.OrderedAdditionalListByRentalCarIdDto;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.lists.OrderedAdditionalListDto;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.additionalExceptions.AdditionalNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.orderedAdditionalExceptions.*;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.RentalCarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;

import java.util.List;

public interface OrderedAdditionalService {

    DataResult<List<OrderedAdditionalListDto>> getAll();

    Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws RentalCarNotFoundException;
    Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws OrderedAdditionalNotFoundException;
    Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws OrderedAdditionalNotFoundException;

    DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(int orderedAdditionalId) throws OrderedAdditionalNotFoundException;
    DataResult<List<OrderedAdditionalListByRentalCarIdDto>> getByOrderedAdditional_RentalCarId(int rentalCarId) throws RentalCarNotFoundException;
    DataResult<List<OrderedAdditionalListByAdditionalIdDto>> getByOrderedAdditional_AdditionalId(int additionalId) throws AdditionalNotFoundException;
    OrderedAdditional getById(int orderedAdditionalId);

    void checkIsExistsByOrderedAdditionalId(int orderedAdditionalId) throws OrderedAdditionalNotFoundException;
    void checkIsNotExistsByOrderedAdditional_RentalCarId(int rentalCarId) throws RentalCarAlreadyExistsInOrderedAdditionalException;
    void checkIsNotExistsByOrderedAdditional_AdditionalId(int additionalId) throws AdditionalAlreadyExistsInOrderedAdditionalException;
    void checkIsOnlyOneOrderedAdditionalByAdditionalIdAndRentalCarIdForUpdate(int additionalId, int rentalCarId) throws OrderedAdditionalAlreadyExistsException;
    void checkAllValidationForAddOrderedAdditionalList(List<CreateOrderedAdditionalRequest> orderedAdditionalRequestList) throws AdditionalQuantityNotValidException, AdditionalNotFoundException;
    void checkAllValidationForAddOrderedAdditional(int additionalId, int orderedAdditionalQuantity) throws AdditionalQuantityNotValidException, AdditionalNotFoundException;

    void saveOrderedAdditionalList(List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList, int rentalCarId) throws RentalCarNotFoundException;

    double getPriceCalculatorForOrderedAdditional(int additionalId, double orderedAdditionalQuantity, int totalDays) throws AdditionalNotFoundException;
    double getPriceCalculatorForOrderedAdditionalListByRentalCarId(int rentalCarId, int totalDays) throws AdditionalNotFoundException;
    double getPriceCalculatorForOrderedAdditionalList(List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList, int totalDays) throws AdditionalNotFoundException;

}
