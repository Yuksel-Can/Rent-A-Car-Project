package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class BrandExistsInCarException extends BusinessException {

    public BrandExistsInCarException(String message) {
        super(message);
    }
}
