package com.turkcell.rentACarProject.business.dtos.corporateCustomerDtos.gets;

import com.turkcell.rentACarProject.business.dtos.customerDtos.gets.GetCustomerDto;
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
