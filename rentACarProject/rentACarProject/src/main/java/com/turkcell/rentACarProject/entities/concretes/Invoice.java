package com.turkcell.rentACarProject.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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

    @Column(name = "invoice_no", unique = true, length = 16, nullable = false)
    private String invoiceNo;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name = "finish_date", nullable = false, updatable = false)
    private LocalDate finishDate;

    @Column(name = "total_rental_day", nullable = false, updatable = false)
    private short totalRentalDay;

    @Column(name = "price_of_days", nullable = false, updatable = false)
    private double priceOfDays;

    @Column(name = "price_of_diff_city", updatable = false)
    private double priceOfDiffCity;

    @Column(name = "price_of_additional", updatable = false)
    private double priceOfAdditionals;

    @Column(name = "rental_car_total_price", nullable = false, updatable = false)
    private double rentalCarTotalPrice;

    @ManyToOne
    @JoinColumn(name = "rental_car_id", nullable = false, updatable = false)
    private RentalCar rentalCar;

    @ManyToOne
    @Cascade(CascadeType.REMOVE)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @OneToOne()
    @JoinColumn(name = "payment_id", unique = true, nullable = false, updatable = false)
    private Payment payment;

}
