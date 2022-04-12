package com.turkcell.rentACarProject.business.requests.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest extends UpdateCustomerRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 300)
    private String companyName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{10}", message = "not number") //todo:açıklama düzelt
    private String taxNumber;

}
