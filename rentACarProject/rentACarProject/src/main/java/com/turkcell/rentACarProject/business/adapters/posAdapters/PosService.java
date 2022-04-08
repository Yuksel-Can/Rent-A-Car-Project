package com.turkcell.rentACarProject.business.adapters.posAdapters;

import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;

public interface PosService {

    boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException;

}
