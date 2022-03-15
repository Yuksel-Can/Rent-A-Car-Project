package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedAdditionalDao extends JpaRepository<OrderedAdditional, Integer> {

    boolean existsByOrderedAdditionalId(int orderedAdditionalId);

}
