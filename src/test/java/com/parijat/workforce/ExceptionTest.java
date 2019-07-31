package com.parijat.workforce;

import com.parijat.workforce.exception.Error;
import com.parijat.workforce.exception.GlobalExceptionHandler;
import com.parijat.workforce.exception.WorkforceValidationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * The type Exception test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExceptionTest
{
	/**
	 * Global exception handler test.
	 */
	@Test
	public void GlobalExceptionHandlerTest()
	{
		WorkforceValidationException exception = new WorkforceValidationException(
				"ERROR"
		);
		
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		Error actual = handler.handleValidationException(exception);
		
		Error expected = new Error();
		expected.setErrorCode("422");
		expected.setMessage("Validation Exception : "+exception.getMessage());
		expected.setTimestamp(new Date());
		
		Assert.assertEquals(expected.getMessage(), actual.getMessage());
		Assert.assertEquals(expected.getErrorCode(), actual.getErrorCode());
		
		
		exception = new WorkforceValidationException(
				"ERROR", "ERROR 2"
		);
		
		handler = new GlobalExceptionHandler();
		actual = handler.handleValidationException(exception);
		
		expected = new Error();
		expected.setErrorCode("422");
		expected.setMessage("Validation Exception : "+exception.getMessage());
		
		Assert.assertEquals(expected.getMessage(), actual.getMessage());
		Assert.assertEquals(expected.getErrorCode(), actual.getErrorCode());
	}
}
