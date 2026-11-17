package com.khan.studentmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException ex){
		ErrorResponse error=new ErrorResponse();
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(java.time.LocalDateTime.now().toString());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
				
	}
	@ExceptionHandler(SubjectNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSubjectNotFound(SubjectNotFoundException ex){
		ErrorResponse error=new ErrorResponse();
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(java.time.LocalDateTime.now().toString());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	//Validation
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex){
			
			ErrorResponse error=new ErrorResponse();
			
			error.setMessage(ex.getBindingResult()
					.getFieldError()
					.getDefaultMessage()
					);
			error.setStatus(HttpStatus.BAD_REQUEST.value());
			error.setTimestamp(java.time.LocalDateTime.now().toString());
			
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		
	//generic exception handling
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex){
		ErrorResponse error=new ErrorResponse();
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setTimestamp(java.time.LocalDateTime.now().toString());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Conflic- duplicate
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException ex){
		ErrorResponse error=new ErrorResponse();
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.CONFLICT.value());
		error.setTimestamp(java.time.LocalDateTime.now().toString());
		
		return new ResponseEntity<> (error, HttpStatus.CONFLICT);
	}
	

}
