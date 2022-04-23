package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class InvoiceNotFoundException extends BusinessException {

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
