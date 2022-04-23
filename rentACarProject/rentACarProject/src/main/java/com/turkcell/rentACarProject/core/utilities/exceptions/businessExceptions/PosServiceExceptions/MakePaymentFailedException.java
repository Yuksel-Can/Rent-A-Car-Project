package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.PosServiceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class MakePaymentFailedException extends BusinessException {

    public MakePaymentFailedException(String message) {
        super(message);
    }
}
