package com.parijat.workforce.utility;

import com.parijat.workforce.exception.WorkforceValidationException;
import com.parijat.workforce.model.CleanerSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * The type Utility.
 */
@Component
@Slf4j
public class Utility
{
	/**
	 * Compute total capacity of a Cleaner Set object.
	 *
	 * @param cleanerSet     the cleaner set object
	 * @param seniorCapacity the senior cleaning capacity
	 * @param juniorCapacity the junior cleaning capacity
	 * @return the integer
	 */
	public Integer computeTotalCapacity(CleanerSet cleanerSet, Integer seniorCapacity, Integer juniorCapacity)
    {
        return ((seniorCapacity*cleanerSet.getSenior())+juniorCapacity*cleanerSet.getJunior());
    }
	
	/**
	 * Handle validation errors.
	 *
	 * @param errors the errors
	 */
	public void handleValidationErrors(Errors errors)
    {
        if(errors.hasErrors())
        {
            StringBuilder messageString = new StringBuilder();
    
            for(ObjectError error : errors.getAllErrors())
            {
                if(messageString.length() == 0)
                {
                    messageString.append(error.getDefaultMessage());
                }
                else
                {
                    messageString.append(" , "+error.getDefaultMessage());
                }
            }
            
            log.error("Request Body has errors. Message : "+messageString.toString());
            throw new WorkforceValidationException(messageString.toString());
        }
        else
        {
            log.debug("Request Body validation done. No error found.");
        }
    }
}
