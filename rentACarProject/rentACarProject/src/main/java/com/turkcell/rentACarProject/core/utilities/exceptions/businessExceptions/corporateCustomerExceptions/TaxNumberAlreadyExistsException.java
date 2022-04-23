package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.corporateCustomerExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class TaxNumberAlreadyExistsException extends BusinessException {

    public TaxNumberAlreadyExistsException(String message) {
        super(message);
    }
}
