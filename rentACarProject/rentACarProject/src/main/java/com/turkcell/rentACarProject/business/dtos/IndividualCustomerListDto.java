package com.turkcell.rentACarProject.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerListDto extends CustomerListDto{

    private String firstName;
    private String lastName;
    private String nationalIdentity;

}