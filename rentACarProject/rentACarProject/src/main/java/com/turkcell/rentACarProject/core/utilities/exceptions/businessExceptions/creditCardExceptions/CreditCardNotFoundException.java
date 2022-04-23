package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.creditCardExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CreditCardNotFoundException extends BusinessException {

    public CreditCardNotFoundException(String message) {
        super(message);
    }
}
