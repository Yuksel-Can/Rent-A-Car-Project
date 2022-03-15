package com.turkcell.rentACarProject.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordered_additionals")
public class OrderedAdditional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_additional_id")
    private int orderedAdditionalId;

    @Column(name = "ordered_additional_quantity")
    private short orderedAdditionalQuantity;

    @ManyToOne
    @JoinColumn(name = "additional_id")
    private Additional additional;

    @ManyToOne
    @JoinColumn(name = "rental_car_id")
    private RentalCar rentalCar;

}