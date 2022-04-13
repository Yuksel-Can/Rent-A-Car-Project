package com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.lists;

import com.turkcell.rentACarProject.business.dtos.customerDtos.lists.CustomerListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerListDto extends CustomerListDto {

    private String firstName;
    private String lastName;
    private String nationalIdentity;

}