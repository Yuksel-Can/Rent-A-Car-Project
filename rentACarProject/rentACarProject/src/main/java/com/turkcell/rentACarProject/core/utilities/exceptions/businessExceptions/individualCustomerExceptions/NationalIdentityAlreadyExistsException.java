package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.individualCustomerExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class NationalIdentityAlreadyExistsException extends BusinessException {

    public NationalIdentityAlreadyExistsException(String message) {
        super(message);
    }
}
