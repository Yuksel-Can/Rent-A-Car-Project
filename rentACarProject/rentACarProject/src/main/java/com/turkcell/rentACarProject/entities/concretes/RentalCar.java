package com.turkcell.rentACarProject.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class RentalCar {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "rental_car_id")
    private int rentalCarId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name = "rental_car_total_price")
    private double rentalCarTotalPrice;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne()
    @JoinColumn(name ="rented_city", referencedColumnName = "city_id", insertable = false, updatable = false)
    private City rentedCity;

    @ManyToOne()
    @JoinColumn(name = "delivered_city", referencedColumnName = "city_id", insertable = false, updatable = false)
    private City deliveredCity;


    @OneToMany(mappedBy = "rentalCar")
    private List<OrderedAdditional> orderedAdditionals;

}
