package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.concretes.CreditCardManager;
import com.turkcell.rentACarProject.business.dtos.creditCardDtos.gets.GetCreditCardDto;
import com.turkcell.rentACarProject.business.dtos.creditCardDtos.lists.CreditCardListDto;
import com.turkcell.rentACarProject.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.creditCardExceptions.CreditCardAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.creditCardExceptions.CreditCardNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.customerExceptions.CustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface CreditCardService {

    DataResult<List<CreditCardListDto>> getAll();

    Result add(CreateCreditCardRequest createCreditCardRequest) throws CustomerNotFoundException;

    DataResult<GetCreditCardDto> getById(int creditCardId) throws CreditCardNotFoundException;
    DataResult<List<CreditCardListDto>> getAllCreditCardByCustomer_CustomerId(int customerId) throws CustomerNotFoundException;

    void checkSaveInformationAndSaveCreditCard(CreateCreditCardRequest createCreditCardRequest, CreditCardManager.CardSaveInformation cardSaveInformation) throws CustomerNotFoundException;

    void checkIfNotExistsByCustomer_CustomerId(int customerId) throws CreditCardAlreadyExistsException;

}
