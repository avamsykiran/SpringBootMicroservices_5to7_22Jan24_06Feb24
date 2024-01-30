package com.cts.empsapi.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class InvalidEmployeeDetailsException extends Exception {

	public InvalidEmployeeDetailsException(BindingResult results) {
		super(
				results.
				getAllErrors().
				stream().
				map(ObjectError::getDefaultMessage).
				reduce("",(m1,m2) -> m1 +","+m2)
		);
	}

	/*
	public InvalidEmployeeDetailsException(BindingResult results) {
		super(convert(results));
	}
	
	String convert(BindingResult results) {
		StringBuilder sb = new StringBuilder();
		for(ObjectError err : results.getAllErrors()) {
			sb.append(err.getDefaultMessage() + ",");
		}
		return sb.toString();
	}
*/
}
