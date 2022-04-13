package com.turkcell.rentACarProject.business.dtos.paymentDtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentDto {

    private int paymentId;
    private double totalPrice;
    private int rentalCarId;
    private int invoiceId;

}
