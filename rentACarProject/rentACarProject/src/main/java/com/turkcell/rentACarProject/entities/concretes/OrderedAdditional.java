package com.turkcell.rentACarProject.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordered_additionals")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler", "rentalCar"})
public class OrderedAdditional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_additional_id")
    private int orderedAdditionalId;

    @Column(name = "ordered_additional_quantity", nullable = false)
    private short orderedAdditionalQuantity;

    @ManyToOne
    @JoinColumn(name = "additional_id", nullable = false)
    private Additional additional;

    @ManyToOne
    @JoinColumn(name = "rental_car_id", nullable = false)
    private RentalCar rentalCar;

}
