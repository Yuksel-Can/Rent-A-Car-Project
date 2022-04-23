package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class ColorNotFoundException extends BusinessException {

    public ColorNotFoundException(String message) {
        super(message);
    }
}
