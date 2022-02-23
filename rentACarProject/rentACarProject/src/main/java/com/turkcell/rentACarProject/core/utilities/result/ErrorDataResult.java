package com.turkcell.rentACarProject.core.utilities.result;

public class ErrorDataResult<T> extends DataResult<T>{
	
	public ErrorDataResult() {
		super(false, null);
	}
	
	public ErrorDataResult(T data) {
		super(false, data);
	}
	
	public ErrorDataResult(String message) {
		super(false, null, message);
	}
	
	public ErrorDataResult(T data, String message) {
		super(false, data, message);
	}

}
