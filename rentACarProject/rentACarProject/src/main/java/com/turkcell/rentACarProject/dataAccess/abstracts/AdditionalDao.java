package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.Additional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalDao extends JpaRepository<Additional, Integer> {

    boolean existsByAdditionalName(String additionalName);
    boolean existsByAdditionalId(int additionalId);

}
