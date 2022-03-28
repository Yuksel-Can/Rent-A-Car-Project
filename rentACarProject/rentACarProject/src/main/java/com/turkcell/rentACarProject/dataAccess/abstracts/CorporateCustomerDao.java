package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {

    boolean existsByCorporateCustomerId(int corporateCustomerId);
    boolean existsByTaxNumber(String taxNumber);
    boolean existsByTaxNumberAndCorporateCustomerIdIsNot(String taxNumber, int corporateCustomerId);
}
