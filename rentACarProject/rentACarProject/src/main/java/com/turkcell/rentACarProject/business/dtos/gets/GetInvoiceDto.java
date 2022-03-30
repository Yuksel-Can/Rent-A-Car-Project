package com.turkcell.rentACarProject.business.dtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceDto {

    private int invoiceId;
    private String invoiceNo;
    private LocalDate creationDate;
    private int rentalCarId;
    private LocalDate startDate;
    private LocalDate finishDate;
    private short totalRentalDay;
    private double rentalCarTotalPrice;
    private String email;
    //todo:burayada ad soyad
}
