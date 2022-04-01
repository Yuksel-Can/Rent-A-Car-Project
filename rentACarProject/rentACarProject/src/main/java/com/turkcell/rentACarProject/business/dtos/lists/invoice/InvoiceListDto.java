package com.turkcell.rentACarProject.business.dtos.lists.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {

    private int invoiceId;
    private String invoiceNo;
    private LocalDate creationDate;
    private int rentalCarId;
    private LocalDate startDate;
    private LocalDate finishDate;
    private short totalRentalDay;
    private double rentalCarTotalPrice;
    private String email;
    private int customerId;
    //todo:buraya ad soyad vs

}
