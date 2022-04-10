package com.turkcell.rentACarProject.business.requests.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

    //todo:burası düzelcek

    @JsonIgnore
    private double totalPrice;

    @JsonIgnore
    private int rentalCarId;

}
