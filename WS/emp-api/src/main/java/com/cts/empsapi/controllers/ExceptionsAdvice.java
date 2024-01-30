package com.cts.empsapi.controllers;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.empsapi.exceptions.EmployeeNotFoundException;
import com.cts.empsapi.exceptions.InvalidEmployeeDetailsException;
import com.cts.empsapi.models.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionsAdvice {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException exp,HttpRequest request){
		log.error(exp.getMessage(), exp);
		return new ResponseEntity<>(
				new ErrorResponse(LocalDateTime.now(),404,"not found",exp.getMessage(),request.uri().getPath())
				,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidEmployeeDetailsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidEmployeeDetailsException(InvalidEmployeeDetailsException exp,HttpRequest request){
		log.error(exp.getMessage(), exp);
		return new ResponseEntity<>(
				new ErrorResponse(LocalDateTime.now(),400,"bad request",exp.getMessage(),request.uri().getPath())
				,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception exp,HttpRequest request){
		log.error(exp.getMessage(), exp);
		return new ResponseEntity<>(
				new ErrorResponse(LocalDateTime.now(),500,"internal server error",exp.getMessage(),request.uri().getPath())
				,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
