package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalAddModel;
import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalUpdateModel;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForCorporateCustomer;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForIndividualCustomer;
import com.turkcell.rentACarProject.business.dtos.gets.payment.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.lists.payment.PaymentListDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface PaymentService {

    DataResult<List<PaymentListDto>> getAll();

    //--Rental Car
    Result makePaymentForIndividualCustomer(MakePaymentForIndividualCustomer makePaymentForIndividualCustomer) throws BusinessException;
    Result makePaymentForCorporateCustomer(MakePaymentForCorporateCustomer makePaymentForCorporateCustomer) throws BusinessException;

    //--Ordered Additional
    Result makePaymentForOrderedAdditionalAdd(OrderedAdditionalAddModel orderedAdditionalAddModel) throws BusinessException;

    DataResult<GetPaymentDto> getById(int paymentId) throws BusinessException;
    DataResult<List<PaymentListDto>> getAllPaymentByRentalCar_RentalCarId(int rentalCarId) throws BusinessException;

}
