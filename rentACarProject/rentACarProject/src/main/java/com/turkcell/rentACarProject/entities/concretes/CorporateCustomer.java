package com.turkcell.rentACarProject.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "corporate_customer_id", referencedColumnName = "customer_id")
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer {

    @Column(name = "corporate_customer_id", insertable = false, updatable = false)
    private int corporateCustomerId;

    @Column(name = "company_name", unique = true,nullable = false)
    private String companyName;

    @Column(name = "tax_number",unique = true, length = 10, nullable = false)
    private String taxNumber;
}
