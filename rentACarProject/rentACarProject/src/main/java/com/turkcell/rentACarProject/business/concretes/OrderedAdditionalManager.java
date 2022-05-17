package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.AdditionalService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
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
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.OrderedAdditionalDao;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalManager implements OrderedAdditionalService {

    private final OrderedAdditionalDao orderedAdditionalDao;
    private final ModelMapperService modelMapperService;
    private final AdditionalService additionalService;
    private final RentalCarService rentalCarService;

    @Autowired
    public OrderedAdditionalManager(OrderedAdditionalDao orderedAdditionalDao, ModelMapperService modelMapperService, AdditionalService additionalService,
                                    RentalCarService rentalCarService) {
        this.orderedAdditionalDao = orderedAdditionalDao;
        this.modelMapperService = modelMapperService;
        this.additionalService = additionalService;
        this.rentalCarService = rentalCarService;
    }


    @Override
    public DataResult<List<OrderedAdditionalListDto>> getAll() {

        List<OrderedAdditional> orderedAdditionalList = this.orderedAdditionalDao.findAll();

        List<OrderedAdditionalListDto> result = orderedAdditionalList.stream().map(orderedAdditional -> this.modelMapperService.forDto()
                .map(orderedAdditional, OrderedAdditionalListDto.class)).collect(Collectors.toList());

        for(int i = 0; i < result.size(); i++){
            result.get(i).setRentalCarId(orderedAdditionalList.get(i).getRentalCar().getRentalCarId());
        }

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws RentalCarNotFoundException {

        this.rentalCarService.checkIsExistsByRentalCarId(createOrderedAdditionalRequest.getRentalCarId());

        OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest, OrderedAdditional.class);
        orderedAdditional.setOrderedAdditionalId(0);

        this.orderedAdditionalDao.save(orderedAdditional);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws OrderedAdditionalNotFoundException {

        checkIsExistsByOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());

        OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(updateOrderedAdditionalRequest, OrderedAdditional.class);
        orderedAdditional.setOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());

        this.orderedAdditionalDao.save(orderedAdditional);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateOrderedAdditionalRequest.getOrderedAdditionalId());
    }

    @Override
    public Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws OrderedAdditionalNotFoundException {

        checkIsExistsByOrderedAdditionalId(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

        OrderedAdditional orderedAdditional = this.orderedAdditionalDao.getById(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

        this.orderedAdditionalDao.deleteById(orderedAdditional.getOrderedAdditionalId());

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + deleteOrderedAdditionalRequest.getOrderedAdditionalId());
    }

    @Override
    public DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(int orderedAdditionalId) throws OrderedAdditionalNotFoundException {

        checkIsExistsByOrderedAdditionalId(orderedAdditionalId);

        OrderedAdditional orderedAdditional = this.orderedAdditionalDao.getById(orderedAdditionalId);

        GetOrderedAdditionalDto result = this.modelMapperService.forDto().map(orderedAdditional, GetOrderedAdditionalDto.class);
        if(result != null) {
            result.setRentalCarId(orderedAdditional.getRentalCar().getRentalCarId());
        }

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + orderedAdditionalId);
    }

    @Override
    public DataResult<List<OrderedAdditionalListByRentalCarIdDto>> getByOrderedAdditional_RentalCarId(int rentalCarId) throws RentalCarNotFoundException {

        this.rentalCarService.checkIsExistsByRentalCarId(rentalCarId);

        List<OrderedAdditional> orderedAdditionalList = this.orderedAdditionalDao.getAllByRentalCar_RentalCarId(rentalCarId);

        List<OrderedAdditionalListByRentalCarIdDto> result = orderedAdditionalList.stream().map(orderedAdditional -> this.modelMapperService
                .forDto().map(orderedAdditional, OrderedAdditionalListByRentalCarIdDto.class)).collect(Collectors.toList());

        if(result.size() != 0){
            for(int i = 0; i < result.size(); i++) {
                result.get(i).setRentalCarId(orderedAdditionalList.get(i).getRentalCar().getRentalCarId());
            }
        }
        return new SuccessDataResult<>(result, BusinessMessages.OrderedAdditionalMessages.ORDERED_ADDITIONAL_LISTED_BY_RENTAL_CAR_ID + rentalCarId);
    }

    @Override
    public DataResult<List<OrderedAdditionalListByAdditionalIdDto>> getByOrderedAdditional_AdditionalId(int additionalId) throws AdditionalNotFoundException {

        this.additionalService.checkIfExistsByAdditionalId(additionalId);

        List<OrderedAdditional> orderedAdditionalList = this.orderedAdditionalDao.getAllByAdditional_AdditionalId(additionalId);

        List<OrderedAdditionalListByAdditionalIdDto> result = orderedAdditionalList.stream().map(orderedAdditional -> this.modelMapperService
                .forDto().map(orderedAdditional, OrderedAdditionalListByAdditionalIdDto.class)).collect(Collectors.toList());

        if(result.size() != 0){
            for(int i = 0; i < result.size(); i++){
                result.get(i).setRentalCarId(orderedAdditionalList.get(i).getRentalCar().getRentalCarId());
            }
        }
        return new SuccessDataResult<>(result, BusinessMessages.OrderedAdditionalMessages.ORDERED_ADDITIONAL_LISTED_BY_ADDITIONAL_ID + additionalId);
    }

    @Override
    public void saveOrderedAdditionalList(List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList, int rentalCarId) throws RentalCarNotFoundException {
        for(CreateOrderedAdditionalRequest createOrderedAdditionalRequest : createOrderedAdditionalRequestList){
            createOrderedAdditionalRequest.setRentalCarId(rentalCarId);
            add(createOrderedAdditionalRequest);
        }
    }

    @Override
    public double getPriceCalculatorForOrderedAdditionalListByRentalCarId(int rentalCarId, int totalDays) throws AdditionalNotFoundException {

        List<OrderedAdditional> orderedAdditionalList = this.orderedAdditionalDao.getAllByRentalCar_RentalCarId(rentalCarId);
        double totalPrice = 0;
        if(orderedAdditionalList != null){
            for (OrderedAdditional orderedAdditional : orderedAdditionalList){
                totalPrice += getPriceCalculatorForOrderedAdditional(orderedAdditional.getAdditional().getAdditionalId(), orderedAdditional.getOrderedAdditionalQuantity(), totalDays);
            }
        }
        return totalPrice;
    }

    @Override
    public double getPriceCalculatorForOrderedAdditionalList(List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList, int totalDays) throws AdditionalNotFoundException {

        double totalPrice = 0;
        if(createOrderedAdditionalRequestList != null){
            for(CreateOrderedAdditionalRequest orderedAdditionalList : createOrderedAdditionalRequestList) {
                totalPrice += getPriceCalculatorForOrderedAdditional(orderedAdditionalList.getAdditionalId(),orderedAdditionalList.getOrderedAdditionalQuantity(), totalDays);
            }
        }
        return totalPrice;
    }

    @Override
    public double getPriceCalculatorForOrderedAdditional(int additionalId, double orderedAdditionalQuantity, int totalDays) throws AdditionalNotFoundException {
        double dailyPrice = this.additionalService.getByAdditionalId(additionalId).getData().getAdditionalDailyPrice();
        return dailyPrice * orderedAdditionalQuantity * totalDays;
    }

    @Override
    public OrderedAdditional getById(int orderedAdditionalId){
        return this.orderedAdditionalDao.getById(orderedAdditionalId);
    }

    @Override
    public void checkAllValidationForAddOrderedAdditional(int additionalId, int orderedAdditionalQuantity) throws AdditionalQuantityNotValidException, AdditionalNotFoundException {
        this.additionalService.checkIfExistsByAdditionalId(additionalId);
        int maxUnitsPerRental = this.additionalService.getByAdditionalId(additionalId).getData().getMaxUnitsPerRental();
        if(orderedAdditionalQuantity > maxUnitsPerRental || orderedAdditionalQuantity < 1){
            throw new AdditionalQuantityNotValidException(BusinessMessages.OrderedAdditionalMessages.ADDITIONAL_QUANTITY_NOT_VALID + maxUnitsPerRental);
        }
    }

    public void checkAllValidationForAddOrderedAdditionalList(List<CreateOrderedAdditionalRequest> orderedAdditionalRequestList) throws AdditionalQuantityNotValidException, AdditionalNotFoundException {
        for (CreateOrderedAdditionalRequest orderedAdditionalRequest : orderedAdditionalRequestList){
            checkAllValidationForAddOrderedAdditional(orderedAdditionalRequest.getAdditionalId(), orderedAdditionalRequest.getOrderedAdditionalQuantity());
        }
    }

    @Override
    public void checkIsOnlyOneOrderedAdditionalByAdditionalIdAndRentalCarIdForUpdate(int additionalId, int rentalCarId) throws OrderedAdditionalAlreadyExistsException {
        if(this.orderedAdditionalDao.getAllByAdditional_AdditionalIdAndRentalCar_RentalCarId(additionalId, rentalCarId).size() > 1){
            throw new OrderedAdditionalAlreadyExistsException(BusinessMessages.OrderedAdditionalMessages.ORDERED_ADDITIONAL_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkIsExistsByOrderedAdditionalId(int orderedAdditionalId) throws OrderedAdditionalNotFoundException {
        if(!this.orderedAdditionalDao.existsByOrderedAdditionalId(orderedAdditionalId)){
            throw new OrderedAdditionalNotFoundException(BusinessMessages.OrderedAdditionalMessages.ORDERED_ADDITIONAL_ID_NOT_FOUND + orderedAdditionalId);
        }
    }

    @Override
    public void checkIsNotExistsByOrderedAdditional_RentalCarId(int rentalCarId) throws RentalCarAlreadyExistsInOrderedAdditionalException {
        if(this.orderedAdditionalDao.existsByRentalCar_RentalCarId(rentalCarId)){
            throw new RentalCarAlreadyExistsInOrderedAdditionalException(BusinessMessages.OrderedAdditionalMessages.RENTAL_CAR_ID_ALREADY_EXISTS_IN_THE_ORDERED_ADDITIONAL_TABLE + rentalCarId);
        }
    }

    @Override
    public void checkIsNotExistsByOrderedAdditional_AdditionalId(int additionalId) throws AdditionalAlreadyExistsInOrderedAdditionalException {
        if(this.orderedAdditionalDao.existsByAdditional_AdditionalId(additionalId)){
            throw new AdditionalAlreadyExistsInOrderedAdditionalException(BusinessMessages.OrderedAdditionalMessages.ADDITIONAL_ID_ALREADY_EXISTS_IN_THE_ORDERED_ADDITIONAL_TABLE+ additionalId);
        }
    }

}
