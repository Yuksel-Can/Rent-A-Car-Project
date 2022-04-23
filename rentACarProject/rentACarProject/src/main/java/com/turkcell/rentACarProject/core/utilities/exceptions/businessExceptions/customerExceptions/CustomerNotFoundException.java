package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.customerExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CustomerNotFoundException extends BusinessException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
