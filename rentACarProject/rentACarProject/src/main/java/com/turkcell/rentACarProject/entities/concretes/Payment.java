package com.turkcell.rentACarProject.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne()
    @JoinColumn(name = "rental_car_id")
    private RentalCar rentalCar;

    @OneToOne(mappedBy = "payment")
    private Invoice invoice;

}
