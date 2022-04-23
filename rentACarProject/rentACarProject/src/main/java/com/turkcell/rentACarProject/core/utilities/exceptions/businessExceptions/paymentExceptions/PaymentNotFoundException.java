package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.paymentExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class PaymentNotFoundException extends BusinessException {

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
