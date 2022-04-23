package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CarAlreadyInMaintenanceException extends BusinessException {

    public CarAlreadyInMaintenanceException(String message) {
        super(message);
    }
}
