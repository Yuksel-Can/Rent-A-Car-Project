package com.turkcell.rentACarProject.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name = "total_rental_day")
    private short totalRentalDay;

    @Column(name = "price_of_days")
    private double priceOfDays;

    @Column(name = "price_of_diff_city")
    private double priceOfDiffCity;

    @Column(name = "price_of_additional")
    private double priceOfAdditionals;

    @Column(name = "rental_car_total_price")
    private double rentalCarTotalPrice;

    @ManyToOne
    @JoinColumn(name = "rental_car_id")
    private RentalCar rentalCar;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
