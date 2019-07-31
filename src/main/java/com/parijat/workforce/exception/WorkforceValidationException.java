package com.parijat.workforce.exception;

/**
 * The type Workforce validation exception.
 */
public class WorkforceValidationException extends BaseException
{
	
	/**
	 * Instantiates a new Workforce validation exception with default message
	 *
	 * @param message the message
	 */
	public WorkforceValidationException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new Workforce validation exception with default message and code
	 *
	 * @param message the message
	 * @param code    the code
	 */
	public WorkforceValidationException(String message, String code)
	{
		super(message, code);
	}
}
