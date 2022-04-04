package com.turkcell.rentACarProject.core.postServices;

import com.turkcell.rentACarProject.core.outServices.AkbankPostService;
import com.turkcell.rentACarProject.core.outServices.ZiraatBankPostService;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class AkbankPost implements PostService{

    @Override
    public void payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException {

        if(!ZiraatBankPostService.payment(cardNumber, cardOwner, cardCvv, cardExpirationDate, totalPrice)){
            throw new BusinessException("payment failed, akbank");
        }
    }
}
