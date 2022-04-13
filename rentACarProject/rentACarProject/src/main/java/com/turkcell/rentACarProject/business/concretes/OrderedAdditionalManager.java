package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.AdditionalService;
import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.gets.GetOrderedAdditionalDto;
import com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.lists.OrderedAdditionalListDto;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.adapters.posAdapters.PosService;
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

    private final OrderedAdditionalDao orderedAdditionalDao;
    private final ModelMapperService modelMapperService;
    private final AdditionalService additionalService;
    private final RentalCarService rentalCarService;
    private final InvoiceService invoiceService;
    private final PosService posService;

    @Autowired
    public OrderedAdditionalManager(OrderedAdditionalDao orderedAdditionalDao, ModelMapperService modelMapperService, AdditionalService additionalService,
                                    RentalCarService rentalCarService, InvoiceService invoiceService, PosService posService) {
        this.orderedAdditionalDao = orderedAdditionalDao;
        this.modelMapperService = modelMapperService;
        this.additionalService = additionalService;
        this.rentalCarService = rentalCarService;
        this.invoiceService = invoiceService;
        this.posService = posService;
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

        this.rentalCarService.checkIsExistsByRentalCarId(createOrderedAdditionalRequest.getRentalCarId());

        OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest, OrderedAdditional.class);
        orderedAdditional.setOrderedAdditionalId(0);

        this.orderedAdditionalDao.save(orderedAdditional);

        return new SuccessResult("Ordered Additional Service added");
    }

    @Override
    public Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {

        checkIsExistsByOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());

        OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(updateOrderedAdditionalRequest, OrderedAdditional.class);
        orderedAdditional.setOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());

        this.orderedAdditionalDao.save(orderedAdditional);

        return new SuccessResult("Ordered Additional Service updated");
    }

    @Override
    public Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException {

        checkIsExistsByOrderedAdditionalId(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

        OrderedAdditional orderedAdditional = this.orderedAdditionalDao.getById(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

        this.orderedAdditionalDao.deleteById(orderedAdditional.getOrderedAdditionalId());

        return new SuccessResult("Ordered Additional Service deleted");
    }

    @Override
    public DataResult<GetOrderedAdditionalDto> getByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException {

        checkIsExistsByOrderedAdditionalId(orderedAdditionalId);

        OrderedAdditional orderedAdditional = this.orderedAdditionalDao.getById(orderedAdditionalId);

        GetOrderedAdditionalDto result = this.modelMapperService.forDto().map(orderedAdditional, GetOrderedAdditionalDto.class);
        result.setRentalCarId(orderedAdditional.getRentalCar().getRentalCarId());

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

    @Override
    public void saveOrderedAdditional(List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList, int rentalCarId) throws BusinessException {
        for(CreateOrderedAdditionalRequest createOrderedAdditionalRequest : createOrderedAdditionalRequestList){
            createOrderedAdditionalRequest.setRentalCarId(rentalCarId);
            add(createOrderedAdditionalRequest);
        }
    }

    @Override
    public double calculateTotalPriceForOrderedAdditionals(int rentalCarId, int totalDays) throws BusinessException {
        //bu ekli olanlara bakarak hesaplıyor
        List<OrderedAdditional> orderedAdditionalList = this.orderedAdditionalDao.getAllByRentalCar_RentalCarId(rentalCarId);

        double totalPrice = 0;
        if(orderedAdditionalList != null){
            for (OrderedAdditional orderedAdditional : orderedAdditionalList){
                totalPrice += getPriceCalculatorForAdditional(orderedAdditional.getAdditional().getAdditionalId(), orderedAdditional.getOrderedAdditionalQuantity(), totalDays);

            }
        }
        return totalPrice;
    }

    @Override
    public double calculateTotalPriceForOrderedAdditionals(List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList, int totalDays) throws BusinessException {
        //buda yollanan listeye bakıyor
        double totalPrice = 0;
        if(createOrderedAdditionalRequestList != null){
            for(CreateOrderedAdditionalRequest orderedAdditionalList : createOrderedAdditionalRequestList) {
                totalPrice += getPriceCalculatorForAdditional(orderedAdditionalList.getAdditionalId(),orderedAdditionalList.getOrderedAdditionalQuantity(), totalDays);
            }
        }
        return totalPrice;
    }

    @Override
    public double getPriceCalculatorForAdditional(int additionalId,double orderedAdditionalQuantity, int totalDays) throws BusinessException {
        double dailyPrice = this.additionalService.getByAdditionalId(additionalId).getData().getAdditionalDailyPrice();
        return dailyPrice * orderedAdditionalQuantity * totalDays;
    }

    @Override
    public OrderedAdditional getById(int orderedAdditionalId){
        return this.orderedAdditionalDao.getById(orderedAdditionalId);
    }

    @Override
    public void checkAllValidationForAddOrderedAdditional(int additionalId, int orderedAdditionalQuantity) throws BusinessException {
        this.additionalService.checkIfExistsByAdditionalId(additionalId);

        int maxUnitsPerRental = this.additionalService.getByAdditionalId(additionalId).getData().getMaxUnitsPerRental();
        if(orderedAdditionalQuantity > maxUnitsPerRental || orderedAdditionalQuantity < 1){
            throw new BusinessException("For this additional service, the Minimum quantity can be 1, the maximum quantity is: " + maxUnitsPerRental);
        }
    }

    @Override
    public void checkAllValidationForAddOrderedAdditional(List<CreateOrderedAdditionalRequest> orderedAdditionalRequestList) throws BusinessException {
        for (CreateOrderedAdditionalRequest orderedAdditionalRequest : orderedAdditionalRequestList){
            checkAllValidationForAddOrderedAdditional(orderedAdditionalRequest.getAdditionalId(), orderedAdditionalRequest.getOrderedAdditionalQuantity());
        }
    }

    @Override
    public void checkIsOnlyOneOrderedAdditionalByAdditionalIdAndRentalCarIdForUpdate(int additionalId, int rentalCarId) throws BusinessException {
        if(this.orderedAdditionalDao.getAllByAdditional_AdditionalIdAndRentalCar_RentalCarId(additionalId, rentalCarId).size() >= 1){   //todo:buraya = koydum kontrol et
            throw new BusinessException("This Ordered is already exists");
        }
    }

    @Override
    public void checkIsExistsByOrderedAdditionalId(int orderedAdditionalId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByOrderedAdditionalId(orderedAdditionalId)){
            throw new BusinessException("Ordered Additional id not exists");
        }
    }

    private void checkIsExistsByRentalCarId(int rentalCarId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByRentalCar_RentalCarId(rentalCarId)){
            throw new BusinessException("There is a car rental, but there is no ordered additional service for this car rental");
        }
    }

    @Override
    public void checkIsNotExistsByOrderedAdditional_RentalCarId(int rentalCarId) throws BusinessException {
        if(this.orderedAdditionalDao.existsByRentalCar_RentalCarId(rentalCarId)){
            throw new BusinessException("Rental Car Id is available in the ordered additional table, rentalCarId: " + rentalCarId);
        }
    }

    @Override
    public void checkIsNotExistsByOrderedAdditional_AdditionalId(int additionalId) throws BusinessException {
        if(this.orderedAdditionalDao.existsByAdditional_AdditionalId(additionalId)){
            throw new BusinessException("Additional Id is available in the ordered additional table, additionalId: " + additionalId);
        }
    }

    private void checkIsExistsByAdditionalId(int additionalId) throws BusinessException {
        if(!this.orderedAdditionalDao.existsByAdditional_AdditionalId(additionalId)){
            throw new BusinessException("there is no ordered additional service for this additional");
        }
    }

}
