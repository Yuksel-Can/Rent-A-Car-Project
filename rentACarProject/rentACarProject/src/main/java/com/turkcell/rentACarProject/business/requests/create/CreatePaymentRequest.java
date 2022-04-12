package com.turkcell.rentACarProject.business.requests.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CreatePaymentRequest {

    @JsonIgnore
    private double totalPrice;

    @JsonIgnore
    private int rentalCarId;

    //todo:bir üstü jsonignore burası notnull bunu kontrol et
}
