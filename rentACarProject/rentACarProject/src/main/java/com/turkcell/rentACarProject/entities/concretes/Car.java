package com.turkcell.rentACarProject.entities.concretes;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_id")
	private int carId;
	
	@Column(name="daily_price",nullable = false)
	private double dailyPrice;
	
	@Column(name="model_year", length = 4, nullable = false)
	private int modelYear;
	
	@Column(name="description")
	private String description;

	@Column(name = "kilometer",nullable = false)
	private int kilometer;
	
	@ManyToOne()
	@JoinColumn(name="brand_id", nullable = false)
	private Brand brand;
	
	@ManyToOne()
	@JoinColumn(name="color_id", nullable = false)
	private Color color;

	@OneToMany(mappedBy = "car")
	private List<CarMaintenance> carMaintenances;

	@OneToMany(mappedBy = "car")
	private List<RentalCar> rentalCars;

	@OneToMany(mappedBy = "car")
	private List<CarCrash> carCrashes;
	
}
