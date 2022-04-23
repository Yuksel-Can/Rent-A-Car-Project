package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class BrandNotFoundException extends BusinessException {

    public BrandNotFoundException(String message) {
        super(message);
    }
}
