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
public class CreatePaymentRequest {

    //todo:burası düzelcek

    //@NotNull
   // @Min(1)
    @JsonIgnore
    private double totalPrice;

    //@NotNull
    //@Min(1)
    @JsonIgnore
    private int rentalCarId;

    //todo:bir üstü jsonignore burası notnull bunu kontrol et
}
