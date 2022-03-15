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
@Table(name = "additionals")
public class Additional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_id")
    private int additionalId;

    @Column(name = "additional_name")
    private String additionalName;

    @Column(name = "additional_daily_price")
    private double additionalDailyPrice;

    @Column(name = "max_units_per_rental")
    private short maxUnitsPerRental;

    @OneToMany(mappedBy = "additional")
    private List<OrderedAdditional> orderedAdditionals;

}
