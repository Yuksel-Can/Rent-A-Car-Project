package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalAddModel;
import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalUpdateModel;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForCorporateRentAdd;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForCorporateRentUpdate;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForIndividualRentAdd;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForIndividualRentUpdate;
import com.turkcell.rentACarProject.business.dtos.gets.payment.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.lists.payment.PaymentListDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface PaymentService {

    DataResult<List<PaymentListDto>> getAll();

    //--Rental Car
    Result makePaymentForIndividualRentAdd(MakePaymentForIndividualRentAdd makePaymentForIndividualRentAdd) throws BusinessException;
    Result makePaymentForCorporateRentAdd(MakePaymentForCorporateRentAdd makePaymentForCorporateRentAdd) throws BusinessException;
    Result makePaymentForIndividualRentUpdate(MakePaymentForIndividualRentUpdate makePaymentForIndividualRentUpdate) throws BusinessException;
    Result makePaymentForCorporateRentUpdate(MakePaymentForCorporateRentUpdate makePaymentForCorporateRentUpdate) throws BusinessException;

    //--Ordered Additional
    Result makePaymentForOrderedAdditionalAdd(OrderedAdditionalAddModel orderedAdditionalAddModel) throws BusinessException;
    Result makePaymentForOrderedAdditionalUpdate(OrderedAdditionalUpdateModel orderedAdditionalUpdateModel) throws BusinessException;

    DataResult<GetPaymentDto> getById(int paymentId) throws BusinessException;
    DataResult<List<PaymentListDto>> getAllPaymentByRentalCar_RentalCarId(int rentalCarId) throws BusinessException;

}