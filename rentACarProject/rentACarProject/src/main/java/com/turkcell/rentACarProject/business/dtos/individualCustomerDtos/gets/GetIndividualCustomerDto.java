package com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.gets;

import com.turkcell.rentACarProject.business.dtos.customerDtos.gets.GetCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetIndividualCustomerDto  extends GetCustomerDto {

    private String firstName;
    private String lastName;
    private String nationalIdentity;

}