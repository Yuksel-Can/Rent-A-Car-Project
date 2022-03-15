package com.turkcell.rentACarProject.business.requests.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {

    @NotNull
    @Min(1)
    private int cityId;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String cityName;

}
