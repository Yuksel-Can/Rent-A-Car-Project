package com.turkcell.rentACarProject.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.entities.abstracts.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "customer_id", referencedColumnName = "user_id")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "customers")
public class Customer extends User {

    @Column(name = "customer_id", insertable = false, updatable = false)
    private int customerId;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "registration_date", updatable = false)
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "customer")
    private List<RentalCar> rentedCars;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CreditCard> creditCards;
}
