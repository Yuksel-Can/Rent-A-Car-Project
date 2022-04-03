package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.CarCrash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarCrashDao extends JpaRepository<CarCrash, Integer> {

    boolean existsByCarCrashId(int carCrashId);
    boolean existsByCar_CarId(int carId);

    List<CarCrash> getAllByCar_CarId(int carId);
}
