package com.turkcell.rentACarProject.core.postServices;

import com.turkcell.rentACarProject.core.outServices.ZiraatBankPostService;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ZiraatBankPost implements PostService {

    @Override
    public void payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException {

        System.out.println("ziraate gelindi");
         if(!ZiraatBankPostService.payment(cardNumber, cardOwner, cardCvv, cardExpirationDate, totalPrice)){
             throw new BusinessException("payment failed, ziraat");
         }
        System.out.println("ziraat ödeme alındı");
    }
}
