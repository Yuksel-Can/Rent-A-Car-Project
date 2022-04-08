package com.turkcell.rentACarProject.business.adapters.posAdapters;

import com.turkcell.rentACarProject.business.outServices.AkbankPosService;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class AkbankPosAdapter implements PosService {

    @Override
    public boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException {

        AkbankPosService akbankPosService = new AkbankPosService();

        if(!akbankPosService.makePayment(cardOwner, cardNumber, cardCvv, cardExpirationDate, totalPrice)){
            throw new BusinessException("payment failed, akbank");
        }
        return true;
    }
}
