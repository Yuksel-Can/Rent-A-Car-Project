package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CustomerNotFoundInInvoiceException extends BusinessException {

    public CustomerNotFoundInInvoiceException(String message) {
        super(message);
    }
}
