package com.nagarro.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.nagarro.training.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
		ErrorResponse response = new ErrorResponse("An error occured: " + e.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e, WebRequest request) {
		ErrorResponse response = new ErrorResponse("An error occured: " + e.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException e, WebRequest request) {
		ErrorResponse response = new ErrorResponse(e.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RecordAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleRecordAlreadyExistsException(RecordAlreadyExistsException e, WebRequest request) {
		ErrorResponse response = new ErrorResponse(e.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
