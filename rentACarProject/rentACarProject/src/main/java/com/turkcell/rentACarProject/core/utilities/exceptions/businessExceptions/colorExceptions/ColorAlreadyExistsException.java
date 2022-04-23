package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class ColorAlreadyExistsException extends BusinessException {

    public ColorAlreadyExistsException(String message) {
        super(message);
    }
}
