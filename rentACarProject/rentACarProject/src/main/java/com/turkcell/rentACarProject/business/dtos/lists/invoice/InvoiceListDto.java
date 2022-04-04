package com.turkcell.rentACarProject.business.dtos.lists.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.entities.concretes.Customer;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {

    private int invoiceId;
    private String invoiceNo;
    private Date creationDate;
    private int rentalCarId;
    private LocalDate startDate;
    private LocalDate finishDate;
    private short totalRentalDay;
    private double priceOfDays;
    private double priceOfDiffCity;
    private double priceOfAdditionals;
    private double rentalCarTotalPrice;
    private int customerId;
    private String email;
    private int paymentId;

    //todo:buraya ad soyad vs
}
