package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.creditCardExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CreditCardAlreadyExistsException extends BusinessException {

    public CreditCardAlreadyExistsException(String message) {
        super(message);
    }
}
