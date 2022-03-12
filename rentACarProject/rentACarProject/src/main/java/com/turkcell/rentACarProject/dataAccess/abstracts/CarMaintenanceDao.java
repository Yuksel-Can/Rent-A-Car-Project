package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;

import java.util.List;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer>{

    List<CarMaintenance> findByCar_CarId(int carId);

    boolean existsByMaintenanceId(int carManintenanceId);
    boolean existsByCar_CarId(int carId);
}
