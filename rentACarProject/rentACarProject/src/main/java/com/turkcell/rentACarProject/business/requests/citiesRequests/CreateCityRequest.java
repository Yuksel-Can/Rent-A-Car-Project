package com.turkcell.rentACarProject.business.requests.citiesRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String cityName;

}
