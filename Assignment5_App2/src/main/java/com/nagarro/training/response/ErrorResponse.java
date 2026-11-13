package com.nagarro.training.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String message;
	
	private String details;
	
	private LocalDateTime timestamp;

	public ErrorResponse(String message, String details) {
		this.message = message;
		this.details = details;
		this.timestamp = LocalDateTime.now();
	}
	
}
