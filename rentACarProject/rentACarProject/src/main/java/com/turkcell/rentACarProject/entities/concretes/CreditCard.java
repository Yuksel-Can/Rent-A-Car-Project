package com.turkcell.rentACarProject.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_card_id")
    private int creditCardId;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_owner")
    private String cardOwner;

    @Column(name = "card_cvv")
    private String cardCvv;

    @Column(name = "card_expiration_date")
    private String cardExpirationDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}