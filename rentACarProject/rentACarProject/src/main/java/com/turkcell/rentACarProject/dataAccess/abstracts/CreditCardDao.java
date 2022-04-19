package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {

    boolean existsByCreditCardId(int creditCardId);
    boolean existsByCustomer_CustomerId(int customerId);
    boolean existsByCardNumber(String cardNumber);

    List<CreditCard> getAllByCustomer_CustomerId(int customerId);

}
