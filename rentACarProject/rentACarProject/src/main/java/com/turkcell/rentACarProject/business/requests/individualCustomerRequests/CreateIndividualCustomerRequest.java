package com.turkcell.rentACarProject.business.requests.individualCustomerRequests;

import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.business.requests.customerRequests.CreateCustomerRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest extends CreateCustomerRequest {

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String lastName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{11}", message = BusinessMessages.IndividualCustomerMessages.NATIONAL_IDENTITY_NOT_VALID)
    private String nationalIdentity;

}
