package com.turkcell.rentACarProject.business.dtos.gets.corporateCustomer;

import com.turkcell.rentACarProject.business.dtos.GetCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCorporateCustomerDto extends GetCustomerDto {

    private String companyName;
    private String taxNumber;

}
