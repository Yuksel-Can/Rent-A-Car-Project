package com.turkcell.rentACarProject.entities.concretes;

import java.time.LocalDate;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_maintenances")
public class CarMaintenance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "maintenance_id")
	private int maintenanceId;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "return_Date")
	private LocalDate returnDate;

	@ManyToOne()
	@JoinColumn(name = "car_id", nullable = false)
	private Car car;
	
}
