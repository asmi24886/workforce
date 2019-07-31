package com.parijat.workforce.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Error object
 */
@Getter
@Setter
public class Error implements Serializable
{
	private static final long serialVersionUID = -1325725054820828328L;
	
	@JsonProperty(value ="timestamp")
	private Date timestamp;
	@JsonProperty(value ="message")
	private String message;
	@JsonProperty(value ="errorCode")
	private String errorCode;
	
	/**
	 * Instantiates a new Error object.
	 */
	public Error()
	{
		super();
	}
	
	/**
	 * Instantiates a new Error object.
	 *
	 * @param timestamp the timestamp of the error occurrence
	 * @param message   the reason of the error
	 * @param errorCode the error code
	 */
	public Error(Date timestamp, String message, String errorCode)
	{
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.errorCode = errorCode;
	}
}
