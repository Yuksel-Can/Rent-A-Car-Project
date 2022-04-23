package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.orderedAdditionalExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class OrderedAdditionalAlreadyExistsException extends BusinessException {

    public OrderedAdditionalAlreadyExistsException(String message) {
        super(message);
    }
}
