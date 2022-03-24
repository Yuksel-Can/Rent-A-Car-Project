package com.turkcell.rentACarProject.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.entities.abstracts.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "customer_id", referencedColumnName = "user_id")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "customers")
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "registration_date")
    private LocalDate registrationDate;

}
