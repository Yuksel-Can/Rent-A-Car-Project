package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

    boolean existsByInvoiceId(int invoiceId);
    boolean existsByInvoiceNo(String invoiceNo);
    boolean existsByPayment_PaymentId(int paymentId);
    boolean existsByCustomer_CustomerId(int customerId);
    boolean existsByRentalCar_RentalCarId(int rentalCarId);
    Invoice getInvoiceByInvoiceNo(String invoiceNo);
    Invoice getInvoiceByPayment_PaymentId(int paymentId);

    List<Invoice> getAllByCustomer_CustomerId(int customerId);
    List<Invoice> getAllByRentalCar_RentalCarId(int rentalCarId);
    List<Invoice> getByCreationDateBetween(Date startDate, Date endDate);

}
