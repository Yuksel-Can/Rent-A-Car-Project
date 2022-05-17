package com.turkcell.rentACarProject.business.adapters.posAdapters;

import com.turkcell.rentACarProject.business.outServices.ZiraatBankPosService;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.PosServiceExceptions.MakePaymentFailedException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ZiraatBankPosAdapter implements PosService {

    @Override
    public boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws MakePaymentFailedException {

        ZiraatBankPosService ziraatBankPosService = new ZiraatBankPosService();

        if(!ziraatBankPosService.makePayment(cardNumber, cardOwner, cardCvv, cardExpirationDate, totalPrice)){
            throw new MakePaymentFailedException("payment failed, Ziraat");
        }
        return true;
    }
}
