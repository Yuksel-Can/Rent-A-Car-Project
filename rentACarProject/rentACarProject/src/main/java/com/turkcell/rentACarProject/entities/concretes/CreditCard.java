package com.turkcell.rentACarProject.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    @Column(name = "card_number", unique = true, length = 16, nullable = false)
    private String cardNumber;

    @Column(name = "card_owner", nullable = false)
    private String cardOwner;

    @Column(name = "card_cvv", length = 3, nullable = false)
    private String cardCvv;

    @Column(name = "card_expiration_date",nullable = false)
    private String cardExpirationDate;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}