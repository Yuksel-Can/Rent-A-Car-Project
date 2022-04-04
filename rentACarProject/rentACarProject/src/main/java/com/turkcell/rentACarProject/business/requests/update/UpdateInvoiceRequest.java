package com.turkcell.rentACarProject.business.requests.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {

    @NotNull
    @Min(1)
    private int invoiceId;

    @JsonIgnore
    @Size(min = 16, max = 16)
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
