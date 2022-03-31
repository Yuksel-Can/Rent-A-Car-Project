package com.turkcell.rentACarProject.business.dtos.gets.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetIndividualCustomerInvoiceDto extends GetInvoiceDto {

    private String firstName;
    private String lastName;
    private String nationalIdentity;

}
