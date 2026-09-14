package comp3011assignment1.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import comp3011assignment1.dto.ErrorResponse;

@RestControllerAdvice
public class UniversalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handlerException(Exception exception, WebRequest request) {
		String path = ((ServletWebRequest) request).getRequest().getRequestURI();

		ErrorResponse response = new ErrorResponse(
				Instant.now(), 
				500,
				"Internal Server Error",
				"An unexpected server error occured.",
				path
			);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	

}
