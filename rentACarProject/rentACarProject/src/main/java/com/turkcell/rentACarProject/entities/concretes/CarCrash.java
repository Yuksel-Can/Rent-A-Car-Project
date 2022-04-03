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

    @Column(name = "crash_date")
    private LocalDate crashDate;

    @Column(name = "crash_valuation")
    private double crashValuation;

    @ManyToOne()
    @JoinColumn(name = "car_id")
    private Car car;

}
