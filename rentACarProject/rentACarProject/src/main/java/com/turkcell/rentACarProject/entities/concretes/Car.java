package com.turkcell.rentACarProject.entities.concretes;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_id")
	private int carId;
	
	@Column(name="daily_price")
	private double dailyPrice;
	
	@Column(name="model_year")
	private int modelYear;
	
	@Column(name="description")
	private String description;
	
	
	@ManyToOne()
	@JoinColumn(name="brand_id")
	private Brand brand;
	
	@ManyToOne()
	@JoinColumn(name="color_id")
	private Color color;

	@OneToMany(mappedBy = "car")
	private List<CarMaintenance> carMaintenances;
	
}
