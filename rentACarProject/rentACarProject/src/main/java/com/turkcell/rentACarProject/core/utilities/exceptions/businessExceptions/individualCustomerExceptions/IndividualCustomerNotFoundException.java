package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.individualCustomerExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class IndividualCustomerNotFoundException extends BusinessException {

    public IndividualCustomerNotFoundException(String message) {
        super(message);
    }
}
