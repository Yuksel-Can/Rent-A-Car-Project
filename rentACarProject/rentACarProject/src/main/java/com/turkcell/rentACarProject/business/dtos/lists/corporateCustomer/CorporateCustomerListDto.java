package com.turkcell.rentACarProject.business.dtos.lists.corporateCustomer;

import com.turkcell.rentACarProject.business.dtos.CustomerListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerListDto extends CustomerListDto {

    private String companyName;
    private String taxNumber;

}
