package com.parijat.workforce.controller;

import com.parijat.workforce.model.CleanerSet;
import com.parijat.workforce.model.RequestModel;
import com.parijat.workforce.processor.IOptimizationProcessor;
import com.parijat.workforce.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Workforce controller. This is the API to check the
 * optimum workforce needed to clean up a given structure.
 */
@RestController
@RequestMapping("workforce")
public class WorkforceController
{
    @Autowired
    private IOptimizationProcessor optimizationProcessor;
    
    @Autowired
    private Utility utility;
	
	/**
	 * The method to provide the optimum combination of
	 * seniors and juniors to optimize overall capacity
	 *
	 * @param request the request body. It includes the input of a
	 *                structure as an array of integers, the cleaning
	 *                capacity of juniors and the cleaning capacity of
	 *                seniors
	 * @param errors  the errors. This contains the validation errors
	 *                (if there is any) occurred due the wrong values of
	 *                the request body
	 * @return the optimum combination of seniors and juniors in JSON.
	 * e.g. : [ {senior: 2, junior: 1}, {senior: 2, junior: 1} ]
	 */
	@PostMapping(
           value = "optimize",
           produces = {
                   MediaType.APPLICATION_JSON_VALUE
           }
   )
    public ResponseEntity<Object> getOptimumSolution(
           @RequestBody @Valid RequestModel request, Errors errors)
    {
        utility.handleValidationErrors(errors);
        
        List<CleanerSet> response = optimizationProcessor.getOptimumSolution(
                request.getRooms(), request.getSenior(), request.getJunior()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
