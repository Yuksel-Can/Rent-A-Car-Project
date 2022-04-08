package com.turkcell.rentACarProject.core.posServices;

import com.turkcell.rentACarProject.core.outServices.ZiraatBankPosService;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ZiraatBankPos implements PosService {

    @Override
    public void payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException {

        System.out.println("ziraate gelindi");
         if(!ZiraatBankPosService.payment(cardNumber, cardOwner, cardCvv, cardExpirationDate, totalPrice)){
             throw new BusinessException("payment failed, ziraat");
         }
        System.out.println("ziraat ödeme alındı");
    }
}
