package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.gets.GetCreditCardDto;
import com.turkcell.rentACarProject.business.dtos.lists.CreditCardListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CreditCardService {

    DataResult<List<CreditCardListDto>> getAll();

    Result add(CreateCreditCardRequest createCreditCardRequest) throws BusinessException;

    DataResult<GetCreditCardDto> getById(int creditCardId) throws BusinessException;
    DataResult<List<CreditCardListDto>> getAllCreditCardByCustomer_CustomerId(int customerId) throws BusinessException;

}
