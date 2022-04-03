package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

    List<Payment> getAllByRentalCar_RentalCarId(int rentalCarId);

    boolean existsByPaymentId(int paymentId);
    boolean existsByRentalCar_RentalCarId(int rentalCarId);

}
