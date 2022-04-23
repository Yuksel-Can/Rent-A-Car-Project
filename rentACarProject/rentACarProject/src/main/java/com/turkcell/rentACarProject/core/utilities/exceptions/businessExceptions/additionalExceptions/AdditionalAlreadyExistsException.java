package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.additionalExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class AdditionalAlreadyExistsException extends BusinessException {

    public AdditionalAlreadyExistsException(String message) {
        super(message);
    }
}
