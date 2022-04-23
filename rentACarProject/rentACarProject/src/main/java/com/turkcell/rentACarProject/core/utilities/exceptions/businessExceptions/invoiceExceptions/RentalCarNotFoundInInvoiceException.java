package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class RentalCarNotFoundInInvoiceException extends BusinessException {

    public RentalCarNotFoundInInvoiceException(String message) {
        super(message);
    }
}
