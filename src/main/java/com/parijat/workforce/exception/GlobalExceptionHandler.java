package com.parijat.workforce.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * The type Global exception handler.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
	/**
	 * Gets message from an exception object
	 *
	 * @param e the exception object
	 * @return the cause / message of the exception
	 */
	public static String getMessage(Exception e)
	{
		return e.getCause() == null ? e.getMessage() : e.getCause().getMessage();
	}
	
	/**
	 * Handle validation exception error.
	 *
	 * @param e the exception
	 * @return the error object
	 */
	@ExceptionHandler(
			value = {
					WorkforceValidationException.class,
					HttpMessageNotReadableException.class
			}
	)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public Error handleValidationException(Exception e)
	{
		log.error(getMessage(e));
		return new Error(new Date(), "Validation Exception : "+ e.getMessage(), "422");
	}
}
