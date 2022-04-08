package com.turkcell.rentACarProject.core.posServices;

import com.turkcell.rentACarProject.core.outServices.ZiraatBankPosService;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class AkbankPos implements PosService {

    @Override
    public void payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException {

        if(!ZiraatBankPosService.payment(cardNumber, cardOwner, cardCvv, cardExpirationDate, totalPrice)){
            throw new BusinessException("payment failed, akbank");
        }
    }
}
