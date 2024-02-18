package com.wishlist.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

//This class is used to handle global exceptions in the application.
@ControllerAdvice
public class GlobalExceptionHandler {

	// This method handles MethodArgumentNotValidException which occurs when a
	// method argument is not valid.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionMessage> MyInvalidDataExpHandler(MethodArgumentNotValidException ma, WebRequest wr) {

		ExceptionMessage me = new ExceptionMessage();
		me.setTimeStamp(LocalDateTime.now()); // Set the timestamp of the exception
		me.setMessage(ma.getBindingResult().getFieldError().getDefaultMessage()); // Set the exception message
		me.setDetails(wr.getDescription(false)); // Set the details of the exception

		// Return the exception message with a BAD_GATEWAY status
		return new ResponseEntity<ExceptionMessage>(me, HttpStatus.BAD_GATEWAY);

	}

	// This method handles all types of exceptions.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionMessage> myAnyExpHandler(Exception e, WebRequest wr) {

		ExceptionMessage me = new ExceptionMessage();
		me.setTimeStamp(LocalDateTime.now()); // Set the timestamp of the exception
		me.setMessage(e.getMessage()); // Set the exception message
		me.setDetails(wr.getDescription(false)); // Set the details of the exception

		// Return the exception message with a BAD_GATEWAY status
		return new ResponseEntity<ExceptionMessage>(me, HttpStatus.BAD_GATEWAY);

	}

	// This method handles NoHandlerFoundException which occurs when no handler is
	// found for a request.
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ExceptionMessage> myNoHandler(NoHandlerFoundException he, WebRequest wr) {

		ExceptionMessage me = new ExceptionMessage();
		me.setTimeStamp(LocalDateTime.now()); // Set the timestamp of the exception
		me.setMessage(he.getMessage()); // Set the exception message
		me.setDetails(wr.getDescription(false)); // Set the details of the exception

		// Return the exception message with a BAD_GATEWAY status
		return new ResponseEntity<ExceptionMessage>(me, HttpStatus.BAD_GATEWAY);

	}

	// This method handles UserException which occurs when there is an exception
	// related to a user.
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ExceptionMessage> myNoHandler(UserException e, WebRequest wr) {

		ExceptionMessage me = new ExceptionMessage();
		me.setTimeStamp(LocalDateTime.now()); // Set the timestamp of the exception
		me.setMessage(e.getMessage()); // Set the exception message
		me.setDetails(wr.getDescription(false)); // Set the details of the exception

		// Return the exception message with a BAD_GATEWAY status
		return new ResponseEntity<ExceptionMessage>(me, HttpStatus.BAD_GATEWAY);

	}

	// This method handles ProductException which occurs when there is an exception
	// related to a product.
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ExceptionMessage> productExceptionhandler(ProductException ex, WebRequest req) {

		ExceptionMessage err = new ExceptionMessage();
		err.setTimeStamp(LocalDateTime.now()); // Set the timestamp of the exception
		err.setMessage(ex.getMessage()); // Set the exception message
		err.setDetails(req.getDescription(false)); // Set the details of the exception

		// Return the exception message with a BAD_GATEWAY status
		return new ResponseEntity<ExceptionMessage>(err, HttpStatus.BAD_GATEWAY);
	}

	// This method handles WishlistException which occurs when there is an exception
	// related to a wishlist.
	@ExceptionHandler(WishlistException.class)
	public ResponseEntity<ExceptionMessage> WishistExceptionhandler(WishlistException ex, WebRequest req) {

		ExceptionMessage err = new ExceptionMessage();
		err.setTimeStamp(LocalDateTime.now()); // Set the timestamp of the exception
		err.setMessage(ex.getMessage()); // Set the exception message
		err.setDetails(req.getDescription(false)); // Set the details of the exception

		// Return the exception message with a BAD_GATEWAY status
		return new ResponseEntity<ExceptionMessage>(err, HttpStatus.BAD_GATEWAY);
	}

}
