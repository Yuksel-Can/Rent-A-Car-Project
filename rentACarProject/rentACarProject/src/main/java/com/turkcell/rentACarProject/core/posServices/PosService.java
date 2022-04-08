package com.turkcell.rentACarProject.core.posServices;

import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;

public interface PosService {

    void payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException;

}
