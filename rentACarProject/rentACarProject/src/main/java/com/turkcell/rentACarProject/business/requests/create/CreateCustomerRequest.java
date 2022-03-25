package com.turkcell.rentACarProject.business.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class CreateCustomerRequest extends CreateUserRequest{

    //@CreationTimestamp
    //private LocalDate registrationDate;

}
