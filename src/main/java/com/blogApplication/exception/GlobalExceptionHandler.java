package com.blogApplication.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogApplication.payload.ApiResponse;

/*@ControllerAdvice annotation allows us to consolidate our multiple, scattered @ExceptionHandlers 
 * from before into a single, global error handling component*/

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
//	This is method is handling exception of user dto class that we used with validation
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgNotValidEx(MethodArgumentNotValidException ex)
	{
		Map<String,String> response = new HashMap<>();
		
//		Now we will get all our custom msgs from field of dto that we have written
		ex.getBindingResult().getAllErrors().forEach((error)-> {
			String fieldName =((FieldError) error).getField();
			String message = error.getDefaultMessage();
			
			response.put(fieldName, message);
		});
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
	}

}
