package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CreditCardService;
import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.business.dtos.creditCardDtos.gets.GetCreditCardDto;
import com.turkcell.rentACarProject.business.dtos.creditCardDtos.lists.CreditCardListDto;
import com.turkcell.rentACarProject.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentACarProject.entities.concretes.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardManager implements CreditCardService {

    private final CreditCardDao creditCardDao;
    private final CustomerService customerService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService, CustomerService customerService) {
        this.creditCardDao = creditCardDao;
        this.customerService = customerService;
        this.modelMapperService = modelMapperService;
    }

    public static enum CardSaveInformation {
        SAVE, DONT_SAVE;
    }

    @Override
    public DataResult<List<CreditCardListDto>> getAll() {

        List<CreditCard> creditCardList = this.creditCardDao.findAll();

        List<CreditCardListDto> result = creditCardList.stream().map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Credit Cards listed");
    }

    @Override
    public Result add(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {

        this.customerService.checkIfCustomerIdExists(createCreditCardRequest.getCustomerId());

        CreditCard creditCard = this.modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);
        creditCard.setCustomer(this.customerService.getCustomerById(createCreditCardRequest.getCustomerId()));

        this.creditCardDao.save(creditCard);

        return new SuccessResult("Credit Card added");
    }

    @Override
    public DataResult<GetCreditCardDto> getById(int creditCardId) throws BusinessException {

        checkIfExistsById(creditCardId);

        CreditCard creditCard = this.creditCardDao.getById(creditCardId);

        GetCreditCardDto result = this.modelMapperService.forDto().map(creditCard, GetCreditCardDto.class);

        return new SuccessDataResult<>(result, "Credit card getted by id, creditCardId: " + creditCardId);
    }

    @Override
    public DataResult<List<CreditCardListDto>> getAllCreditCardByCustomer_CustomerId(int customerId) throws BusinessException {

        checkIfExistsByCustomer_CustomerId(customerId);

        List<CreditCard> creditCardList = this.creditCardDao.getAllByCustomer_CustomerId(customerId);

        List<CreditCardListDto> result = creditCardList.stream().map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Credit card getted by customer id, customerId: " + customerId);
    }

    @Override
    public void checkSaveInformationAndSaveCreditCard(CreateCreditCardRequest createCreditCardRequest, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        if(cardSaveInformation.equals(CreditCardManager.CardSaveInformation.SAVE)){
            add(createCreditCardRequest);
        }
    }

    private void checkIfExistsById(int creditCardId) throws BusinessException {
        if(!this.creditCardDao.existsByCreditCardId(creditCardId)){
            throw new BusinessException("Credit card not found, creditCardId: " + creditCardId);
        }
    }

    private void checkIfExistsByCustomer_CustomerId(int customerId) throws BusinessException {
        if(!this.creditCardDao.existsByCustomer_CustomerId(customerId)){
            throw new BusinessException("Customer id not fount in the credit card table, customerId: " + customerId);
        }
    }

}
