package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class PaymentNotFoundInInvoiceException extends BusinessException {

    public PaymentNotFoundInInvoiceException(String message) {
        super(message);
    }
}
