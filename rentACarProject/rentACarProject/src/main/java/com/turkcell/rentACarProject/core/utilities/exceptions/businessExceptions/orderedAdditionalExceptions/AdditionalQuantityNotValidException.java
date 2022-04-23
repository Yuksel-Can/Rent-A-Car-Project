package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.orderedAdditionalExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class AdditionalQuantityNotValidException extends BusinessException {

    public AdditionalQuantityNotValidException(String message) {
        super(message);
    }
}
