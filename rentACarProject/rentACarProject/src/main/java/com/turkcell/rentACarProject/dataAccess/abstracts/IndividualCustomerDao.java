package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {

    boolean existsByIndividualCustomerId(int individualCustomerId);
    boolean existsByNationalIdentity(String nationalIdentity);
    boolean existsByNationalIdentityAndIndividualCustomerIdIsNot(String nationalIdentity, int individualCustomerId);

}
