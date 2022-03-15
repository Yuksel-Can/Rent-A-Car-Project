package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.AdditionalService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.GetOrderedAdditionalDto;
import com.turkcell.rentACarProject.business.dtos.OrderedAdditionalListDto;
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

        this.additionalService.checkIfExistsByAdditionalId(createOrderedAdditionalRequest.getAdditionalId());
        this.rentalCarService.checkIsExistsByRentalCarId(createOrderedAdditionalRequest.getRentalCarId());
        checkIfTheAdditionalQuantityOrderedIsValid(createOrderedAdditionalRequest);

        OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest, OrderedAdditional.class);
        orderedAdditional.setOrderedAdditionalId(0);

        this.orderedAdditionalDao.save(orderedAdditional);

        return new SuccessResult("Ordered Additional Service added");

    }
    @Override
    public Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {

        checkIsExistsByOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());
        this.additionalService.checkIfExistsByAdditionalId(updateOrderedAdditionalRequest.getAdditionalId());
        this.rentalCarService.checkIsExistsByRentalCarId(updateOrderedAdditionalRequest.getRentalCarId());

        OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(updateOrderedAdditionalRequest, OrderedAdditional.class);
        orderedAdditional.setOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());

        this.orderedAdditionalDao.save(orderedAdditional);

        return new SuccessResult("Ordered Additional Service updated");

    }
    @Override
    public Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException {

        checkIsExistsByOrderedAdditionalId(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

        this.orderedAdditionalDao.deleteById(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

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

    private void checkIsExistsByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByOrderedAdditionalId(orderedAdditionalId)){
            throw new BusinessException("Ordered Additional id not exists");
        }
    }

    private void checkIsExistsByRentalCarId(int rentalCarId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByRentalCar_RentalCarId(rentalCarId)){
            throw new BusinessException("There is a car rental, but there is no ordered additional service for this car rental");
        }
    }

    private void checkIsExistsByAdditionalId(int additionalId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByAdditional_AdditionalId(additionalId)){
            throw new BusinessException("there is no ordered additional service for this additional");
        }
    }

    private void checkIfTheAdditionalQuantityOrderedIsValid(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException {
        int maxUnitsPerRental = this.additionalService.getByAdditionalId(createOrderedAdditionalRequest.getAdditionalId()).getData().getMaxUnitsPerRental();
        if(createOrderedAdditionalRequest.getOrderedAdditionalQuantity() > maxUnitsPerRental){
            throw new BusinessException("Maximum quantity for this additional service can be : " + maxUnitsPerRental);
        }
    }



}
