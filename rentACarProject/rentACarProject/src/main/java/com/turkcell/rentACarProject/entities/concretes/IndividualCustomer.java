package com.turkcell.rentACarProject.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "individual_customer_id", referencedColumnName = "customer_id")
@Table(name = "individua_customers")
public class IndividualCustomer extends Customer{

    @Column(name = "individual_customer_id", insertable = false, updatable = false)
    private int individualCustomerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "national_identity", unique = true, length = 11, nullable = false)
    private String nationalIdentity;

}
