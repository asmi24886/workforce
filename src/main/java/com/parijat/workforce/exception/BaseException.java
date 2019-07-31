package com.parijat.workforce.exception;

/**
 * The type Base exception.
 */
public abstract class BaseException extends RuntimeException
{
	private final String code;
	
	/**
	 * Instantiates a new Base exception.
	 *
	 * @param message the message
	 */
	public BaseException(String message)
	{
		super(message);
		this.code = "0";
	}
	
	/**
	 * Instantiates a new Base exception.
	 *
	 * @param message the message of the exception
	 * @param code    the code of the exception
	 */
	public BaseException(String message, String code)
	{
		super(message);
		this.code = code;
	}
	
	/**
	 * Gets exception code.
	 *
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}
}
