package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.orderedAdditionalExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class RentalCarAlreadyExistsInOrderedAdditionalException extends BusinessException {

    public RentalCarAlreadyExistsInOrderedAdditionalException(String message) {
        super(message);
    }
}
