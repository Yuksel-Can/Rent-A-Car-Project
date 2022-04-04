package com.turkcell.rentACarProject.core.postServices;

import com.turkcell.rentACarProject.core.outServices.AkbankPostService;
import org.springframework.stereotype.Service;

@Service
public class AkbankPost implements PostService{

    @Override
    public boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) {
        return AkbankPostService.payment(cardNumber, cardOwner, cardCvv, cardExpirationDate, totalPrice);
    }
}
