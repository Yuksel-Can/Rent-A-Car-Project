package com.turkcell.rentACarProject.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private int cityId;

    @Column(name = "city_name", unique = true, nullable = false)
    private String cityName;

    @OneToMany(mappedBy = "rentedCity")
    private List<RentalCar> rentedCarsFromCity;

    @OneToMany(mappedBy = "deliveredCity")
    private List<RentalCar> deliveredCarsToCity;
}
