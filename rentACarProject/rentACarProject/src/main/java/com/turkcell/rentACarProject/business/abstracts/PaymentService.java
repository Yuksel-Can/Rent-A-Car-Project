package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalAddModel;
import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalUpdateModel;
import com.turkcell.rentACarProject.api.models.rentalCar.*;
import com.turkcell.rentACarProject.business.concretes.CreditCardManager;
import com.turkcell.rentACarProject.business.dtos.gets.payment.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.lists.payment.PaymentListDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface PaymentService {

    DataResult<List<PaymentListDto>> getAll();

    //--Rental Car
    Result makePaymentForIndividualRentAdd(MakePaymentForIndividualRentAdd makePaymentForIndividualRentAdd, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;
    Result makePaymentForCorporateRentAdd(MakePaymentForCorporateRentAdd makePaymentForCorporateRentAdd, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;
    Result makePaymentForIndividualRentUpdate(MakePaymentForIndividualRentUpdate makePaymentForIndividualRentUpdate, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;
    Result makePaymentForCorporateRentUpdate(MakePaymentForCorporateRentUpdate makePaymentForCorporateRentUpdate, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;

    Result makePaymentForRentDeliveryDateUpdate(MakePaymentForRentDeliveryDateUpdate makePaymentModel, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;

    //--Ordered Additional
    Result makePaymentForOrderedAdditionalAdd(OrderedAdditionalAddModel orderedAdditionalAddModel, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;
    Result makePaymentForOrderedAdditionalUpdate(OrderedAdditionalUpdateModel orderedAdditionalUpdateModel, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;

    DataResult<GetPaymentDto> getById(int paymentId) throws BusinessException;
    DataResult<List<PaymentListDto>> getAllPaymentByRentalCar_RentalCarId(int rentalCarId) throws BusinessException;

    void checkIfExistsByPaymentId(int paymentId) throws BusinessException;

}
