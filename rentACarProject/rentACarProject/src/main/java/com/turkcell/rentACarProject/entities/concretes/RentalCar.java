package com.turkcell.rentACarProject.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.text.html.ListView;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rental_cars")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler", "orderedAdditionals"})
public class RentalCar {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "rental_car_id")
    private int rentalCarId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "finish_date", nullable = false)
    private LocalDate finishDate;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne()
    @JoinColumn(name ="rented_city", nullable = false)
    private City rentedCity;

    @ManyToOne()
    @JoinColumn(name = "delivered_city", nullable = false)
    private City deliveredCity;

    @Column(name = "rented_kilometer")
    private Integer rentedKilometer;

    @Column(name = "delivered_kilometer")
    private Integer deliveredKilometer;

    @OneToMany(mappedBy = "rentalCar")
    private List<OrderedAdditional> orderedAdditionals;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "rentalCar")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "rentalCar")
    private List<Payment> payments;

}
