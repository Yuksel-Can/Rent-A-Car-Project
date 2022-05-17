package com.turkcell.rentACarProject.business.adapters.posAdapters;

import com.turkcell.rentACarProject.business.outServices.AkbankPosService;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.PosServiceExceptions.MakePaymentFailedException;
import org.springframework.stereotype.Service;

@Service
public class AkbankPosAdapter implements PosService {

    @Override
    public boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws MakePaymentFailedException {

        AkbankPosService akbankPosService = new AkbankPosService();

        if(!akbankPosService.makePayment(cardOwner, cardNumber, cardCvv, cardExpirationDate, totalPrice)){
            throw new MakePaymentFailedException("payment failed, Akbank");
        }
        return true;
    }
}
