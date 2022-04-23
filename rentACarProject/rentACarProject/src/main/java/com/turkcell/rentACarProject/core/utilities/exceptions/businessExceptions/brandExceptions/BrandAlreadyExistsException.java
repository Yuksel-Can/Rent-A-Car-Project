package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class BrandAlreadyExistsException extends BusinessException {

    public BrandAlreadyExistsException(String message) {
        super(message);
    }
}
