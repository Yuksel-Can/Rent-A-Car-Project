package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.corporateCustomerExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CorporateCustomerNotFoundException extends BusinessException {

    public CorporateCustomerNotFoundException(String message) {
        super(message);
    }
}
