package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer> {
	
	boolean existsByBrandName(String brandName);
	boolean existsByBrandId(int brandId);

}
