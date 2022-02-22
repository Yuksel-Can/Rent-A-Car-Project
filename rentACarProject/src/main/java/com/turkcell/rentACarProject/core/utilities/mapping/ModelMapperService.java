package com.turkcell.rentACarProject.core.utilities.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
	
	ModelMapper forDto();
	
	ModelMapper forRequest();

}