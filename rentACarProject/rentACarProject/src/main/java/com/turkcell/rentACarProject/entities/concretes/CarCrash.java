package com.turkcell.rentACarProject.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_crashes")
public class CarCrash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_crash_id")
    private int carCrashId;

    @Column(name = "crash_date", nullable = false)
    private LocalDate crashDate;

    @Column(name = "crash_valuation", nullable = false)
    private double crashValuation;

    @ManyToOne()
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

}
