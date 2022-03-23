package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.AdditionalService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.GetOrderedAdditionalDto;
import com.turkcell.rentACarProject.business.dtos.GetRentalCarDto;
import com.turkcell.rentACarProject.business.dtos.OrderedAdditionalListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalForRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.OrderedAdditionalDao;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalManager implements OrderedAdditionalService {

    private OrderedAdditionalDao orderedAdditionalDao;
    private ModelMapperService modelMapperService;
    private AdditionalService additionalService;
    private RentalCarService rentalCarService;

    @Autowired
    public OrderedAdditionalManager(OrderedAdditionalDao orderedAdditionalDao, ModelMapperService modelMapperService, AdditionalService additionalService, RentalCarService rentalCarService) {
        this.orderedAdditionalDao = orderedAdditionalDao;
        this.modelMapperService = modelMapperService;
        this.additionalService = additionalService;
        this.rentalCarService = rentalCarService;
    }


    @Override
    public DataResult<List<OrderedAdditionalListDto>> getAll() {

        List<OrderedAdditional> orderedAdditionals = this.orderedAdditionalDao.findAll();

        List<OrderedAdditionalListDto> result = orderedAdditionals.stream().map(orderedAdditional -> this.modelMapperService.forDto()
                .map(orderedAdditional, OrderedAdditionalListDto.class)).collect(Collectors.toList());

        for(int i = 0; i < result.size(); i++){
            result.get(i).setRentalCarId(orderedAdditionals.get(i).getRentalCar().getRentalCarId());
        }

        return new SuccessDataResult<>(result,"Ordered Additional Service listed");

    }

    @Override
    public Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException {

        checkAllValidation(createOrderedAdditionalRequest.getAdditionalId(), createOrderedAdditionalRequest.getOrderedAdditionalQuantity());
        this.rentalCarService.checkIsExistsByRentalCarId(createOrderedAdditionalRequest.getRentalCarId());
        checkIsNotExistsOrderedAdditionalByAdditionalIdAndRentalCarId(createOrderedAdditionalRequest.getAdditionalId(), createOrderedAdditionalRequest.getRentalCarId());

        OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest, OrderedAdditional.class);
        orderedAdditional.setOrderedAdditionalId(0);

        this.orderedAdditionalDao.save(orderedAdditional);

        calculateAndUpdateRentalCarTotalPriceForAdd(orderedAdditional);

        return new SuccessResult("Ordered Additional Service added");

    }

    @Override
    public Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {

        checkIsExistsByOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());
        checkAllValidation(updateOrderedAdditionalRequest.getAdditionalId(), updateOrderedAdditionalRequest.getOrderedAdditionalQuantity());
        this.rentalCarService.checkIsExistsByRentalCarId(updateOrderedAdditionalRequest.getRentalCarId());
        checkIsOnlyOneOrderedAdditionalByAdditionalIdAndRentalCarIdForUpdate(updateOrderedAdditionalRequest.getAdditionalId(), updateOrderedAdditionalRequest.getRentalCarId());

        OrderedAdditional beforeOrderedAdditional = this.orderedAdditionalDao.getById(updateOrderedAdditionalRequest.getOrderedAdditionalId());

        OrderedAdditional afterOrderedAdditional = this.modelMapperService.forRequest().map(updateOrderedAdditionalRequest, OrderedAdditional.class);
        afterOrderedAdditional.setOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());


        calculateAndUpdateRentalCarTotalPriceForUpdate(beforeOrderedAdditional,afterOrderedAdditional);

        this.orderedAdditionalDao.save(afterOrderedAdditional);

        return new SuccessResult("Ordered Additional Service updated");

    }

    @Override
    public Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException {

        checkIsExistsByOrderedAdditionalId(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

        OrderedAdditional orderedAdditional = this.orderedAdditionalDao.getById(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

        this.orderedAdditionalDao.deleteById(orderedAdditional.getOrderedAdditionalId());

        calculateAndUpdateRentalCarTotalPriceForDelete(orderedAdditional);

        return new SuccessResult("Ordered Additional Service deleted");

    }
    @Override
    public DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException {

        checkIsExistsByOrderedAdditionalId(orderedAdditionalId);

        OrderedAdditional orderedAdditional = this.orderedAdditionalDao.getById(orderedAdditionalId);

        GetOrderedAdditionalDto result = this.modelMapperService.forDto().map(orderedAdditional, GetOrderedAdditionalDto.class);
        result.setRentalCarId(orderedAdditional.getRentalCar().getRentalCarId());   // (*)

        return new SuccessDataResult<>(result, "Ordered Additional Service listed by orderedAdditionalId: " + orderedAdditionalId);

    }

    @Override
    public DataResult<List<OrderedAdditionalListDto>> getByOrderedAdditional_RentalCarId(int rentalCarId) throws BusinessException {

        this.rentalCarService.checkIsExistsByRentalCarId(rentalCarId);
        checkIsExistsByRentalCarId(rentalCarId);

        List<OrderedAdditional> orderedAdditionals = this.orderedAdditionalDao.getAllByRentalCar_RentalCarId(rentalCarId);

        List<OrderedAdditionalListDto> result = orderedAdditionals.stream().map(orderedAdditional -> this.modelMapperService
                .forDto().map(orderedAdditional, OrderedAdditionalListDto.class)).collect(Collectors.toList());

        for(int i = 0; i < result.size(); i++){
            result.get(i).setRentalCarId(orderedAdditionals.get(i).getRentalCar().getRentalCarId());
        }

        return new SuccessDataResult<>(result, "Ordered Additional Service of the Rented Car listed by RentalCarId: " + rentalCarId);
    }

    @Override
    public DataResult<List<OrderedAdditionalListDto>> getByOrderedAdditional_AdditionalId(int additionalId) throws BusinessException {

        this.additionalService.checkIfExistsByAdditionalId(additionalId);
        checkIsExistsByAdditionalId(additionalId);

        List<OrderedAdditional> orderedAdditionals = this.orderedAdditionalDao.getAllByAdditional_AdditionalId(additionalId);

        List<OrderedAdditionalListDto> result = orderedAdditionals.stream().map(orderedAdditional -> this.modelMapperService
                .forDto().map(orderedAdditional, OrderedAdditionalListDto.class)).collect(Collectors.toList());

        for(int i = 0; i < result.size(); i++){
            result.get(i).setRentalCarId(orderedAdditionals.get(i).getRentalCar().getRentalCarId());
        }

        return new SuccessDataResult<>(result, "Ordered Additional Service of the Additional listed by AdditionalId: " + additionalId);
    }

    private void calculateAndUpdateRentalCarTotalPriceForAdd(OrderedAdditional orderedAdditional) throws BusinessException {

        RentalCar rentalCar = this.rentalCarService.getById(orderedAdditional.getRentalCar().getRentalCarId());

        double total = getPriceCalculatorForAdditional(orderedAdditional,rentalCar);
        double previousTotalPrice = rentalCar.getRentalCarTotalPrice();

        rentalCar.setRentalCarTotalPrice(previousTotalPrice + total);
        this.rentalCarService.saveChangesRentalCar(rentalCar);

    }
    private void calculateAndUpdateRentalCarTotalPriceForUpdate(OrderedAdditional beforeOrderedAdditional, OrderedAdditional afterOrderedAdditional) throws BusinessException {

        RentalCar rentalCar = this.rentalCarService.getById(beforeOrderedAdditional.getRentalCar().getRentalCarId());

        double beforeTotalPrice = getPriceCalculatorForAdditional(beforeOrderedAdditional, rentalCar);
        double afterTotalPrice = getPriceCalculatorForAdditional(afterOrderedAdditional, rentalCar);

        double previouesTotalPrice = rentalCar.getRentalCarTotalPrice();

        if(beforeTotalPrice>afterTotalPrice){
            rentalCar.setRentalCarTotalPrice(previouesTotalPrice-(beforeTotalPrice-afterTotalPrice));
        }else{
            rentalCar.setRentalCarTotalPrice(previouesTotalPrice+(afterTotalPrice-beforeTotalPrice));
        }

        this.rentalCarService.saveChangesRentalCar(rentalCar);

    }

    private void calculateAndUpdateRentalCarTotalPriceForDelete(OrderedAdditional orderedAdditional) throws BusinessException {

        RentalCar rentalCar = this.rentalCarService.getById(orderedAdditional.getRentalCar().getRentalCarId());

        double total = getPriceCalculatorForAdditional(orderedAdditional, rentalCar);
        double previousTotalPrice = rentalCar.getRentalCarTotalPrice();

        rentalCar.setRentalCarTotalPrice(previousTotalPrice-total);
        this.rentalCarService.saveChangesRentalCar(rentalCar);

    }

    private double getPriceCalculatorForAdditional(OrderedAdditional orderedAdditional,RentalCar rentalCar) throws BusinessException {

        double dailyPrice = this.additionalService.getByAdditionalId(orderedAdditional.getAdditional().getAdditionalId()).getData().getAdditionalDailyPrice();
        double quantity = orderedAdditional.getOrderedAdditionalQuantity();
        int totalDays = this.rentalCarService.getTotalDaysForRental(rentalCar.getStartDate(), rentalCar.getFinishDate());

        return dailyPrice * quantity * totalDays;

    }

    public boolean checkAllValidation(int additionalId, int orderedAdditionalQuantity) throws BusinessException {

        this.additionalService.checkIfExistsByAdditionalId(additionalId);
        checkIfTheAdditionalQuantityOrderedIsValid(additionalId, orderedAdditionalQuantity);

        return true;

    }

    @Override
    public void checkIsNotExistsOrderedAdditionalByAdditionalIdAndRentalCarId(int additionalId, int rentalCarId) throws BusinessException {
        if(this.orderedAdditionalDao.existsByAdditional_AdditionalIdAndRentalCar_RentalCarId(additionalId, rentalCarId)){
            throw new BusinessException("This Ordered is already exists for this rentalCar, rentalCarId: " + rentalCarId + ", additionalId: " + additionalId);
        }
    }

    private void checkIsOnlyOneOrderedAdditionalByAdditionalIdAndRentalCarIdForUpdate(int additionalId, int rentalCarId) throws BusinessException {
        if(this.orderedAdditionalDao.getAllByAdditional_AdditionalIdAndRentalCar_RentalCarId(additionalId, rentalCarId).size() > 1){
            throw new BusinessException("This Ordered is already exists");
        }
    }

    private void checkIsExistsByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByOrderedAdditionalId(orderedAdditionalId)){
            throw new BusinessException("Ordered Additional id not exists");
        }
    }

    private boolean checkIsExistsByRentalCarId(int rentalCarId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByRentalCar_RentalCarId(rentalCarId)){
            throw new BusinessException("There is a car rental, but there is no ordered additional service for this car rental");
        }
        return true;
    }

    @Override
    public boolean checkIsNotExistsByOrderedAdditional_RentalCarId(int rentalCarId) throws BusinessException {
        if(this.orderedAdditionalDao.existsByRentalCar_RentalCarId(rentalCarId)){
            throw new BusinessException("Rental Car Id is available in the ordered additional table, rentalCarId: " + rentalCarId);
        }
        return true;
    }

    @Override
    public boolean checkIsNotExistsByOrderedAdditional_AdditionalId(int additionalId) throws BusinessException {
        if(this.orderedAdditionalDao.existsByAdditional_AdditionalId(additionalId)){
            throw new BusinessException("Additional Id is available in the ordered additional table, additionalId: " + additionalId);
        }
        return true;
    }

    private void checkIsExistsByAdditionalId(int additionalId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByAdditional_AdditionalId(additionalId)){
            throw new BusinessException("there is no ordered additional service for this additional");
        }
    }

    private void checkIfTheAdditionalQuantityOrderedIsValid( int additionalId, int orderedAdditionalQuantity) throws BusinessException {
        int maxUnitsPerRental = this.additionalService.getByAdditionalId(additionalId).getData().getMaxUnitsPerRental();
        if(orderedAdditionalQuantity > maxUnitsPerRental || orderedAdditionalQuantity < 1){
            throw new BusinessException("For this additional service, the Minimum quantity can be 1, the maximum quantity is: " + maxUnitsPerRental);
        }
    }



}
