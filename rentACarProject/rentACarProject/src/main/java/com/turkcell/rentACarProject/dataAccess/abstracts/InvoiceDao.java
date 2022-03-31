package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

    boolean existsByInvoiceId(int invoiceId);
    boolean existsByInvoiceNo(String invoiceNo);
    Invoice getInvoiceByInvoiceNo(String invoiceNo);

}