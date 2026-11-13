package com.nagarro.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gurveersingh
 *
 */

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RecordAlreadyExistsException extends RuntimeException {
	
	private String exceptionDetail;
	private Object fieldValue;

	public RecordAlreadyExistsException(String exceptionDetail, Integer fieldValue) {
		super(exceptionDetail + " - " + fieldValue);
		this.exceptionDetail = exceptionDetail;
		this.fieldValue = fieldValue;
	}

	public RecordAlreadyExistsException(String exceptionDetail, String fieldValue) {
		super(exceptionDetail + " - " + fieldValue);
		this.exceptionDetail = exceptionDetail;
		this.fieldValue = fieldValue;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}
