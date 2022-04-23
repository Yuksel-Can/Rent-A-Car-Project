package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class ModelYearAfterThisYearException extends BusinessException {

    public ModelYearAfterThisYearException(String message) {
        super(message);
    }
}
