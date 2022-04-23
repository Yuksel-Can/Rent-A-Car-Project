package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CarExistsInCarMaintenanceException extends BusinessException {

    public CarExistsInCarMaintenanceException(String message) {
        super(message);
    }
}
