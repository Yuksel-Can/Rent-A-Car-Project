package com.turkcell.rentACarProject.business.requests.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.entities.concretes.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

    @JsonIgnore
    @Pattern(regexp = "^[0-9]{16}", message = "not number") //todo:açıklama düzelt
    private String invoiceNo;

    @JsonIgnore
    @CreationTimestamp
    private Date creationDate;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate finishDate;

    @NotNull
    @Min(1)
    private short totalRentalDay;

    @NotNull
    @Min(0)
    private double priceOfDays;

    @Min(0)
    private double priceOfDiffCity;

    @Min(0)
    private double priceOfAdditionals;

    @NotNull
    @Min(1)
    private double rentalCarTotalPrice;

    @NotNull
    @Min(1)
    private int rentalCarId;

    @NotNull
    @Min(1)
    private int customerId;

    @NotNull
    @Min(1)
    private int paymentId;

}
