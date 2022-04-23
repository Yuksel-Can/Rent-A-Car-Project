package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.paymentExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class RentalCarAlreadyExistsInPaymentException extends BusinessException {

    public RentalCarAlreadyExistsInPaymentException(String message) {
        super(message);
    }
}
