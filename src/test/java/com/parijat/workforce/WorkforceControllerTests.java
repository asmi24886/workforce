package com.parijat.workforce;

import com.parijat.workforce.controller.WorkforceController;
import com.parijat.workforce.exception.WorkforceValidationException;
import com.parijat.workforce.model.CleanerSet;
import com.parijat.workforce.model.RequestModel;
import com.parijat.workforce.processor.OptimizationProcessorImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Workforce controller tests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WorkforceControllerTests
{
    @Autowired
    private OptimizationProcessorImpl optimizationProcessor;

    @Autowired
    private WorkforceController workforceController;

    private List<CleanerSet> prepareTestData()
    {
        List<CleanerSet> expected = new ArrayList<>();
        CleanerSet cleanerSet = new CleanerSet();
        cleanerSet.setSenior(2);
        cleanerSet.setJunior(1);
        expected.add(cleanerSet);

        cleanerSet = new CleanerSet();
        cleanerSet.setSenior(2);
        cleanerSet.setJunior(1);
        expected.add(cleanerSet);

        return expected;
    }
	
	/**
	 * Test get optimum solution.
	 */
	@Test
    public void Test_getOptimumSolution()
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{24,28});
        request.setSenior(11);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, "request");

        ResponseEntity<Object> expected = new ResponseEntity<>(prepareTestData(), HttpStatus.OK);
        ResponseEntity<Object> actual = workforceController.getOptimumSolution(request,errors);

        Assert.assertEquals(expected,actual);
    }
	
	/**
	 * Test get optimum solution expection.
	 */
	@Test(expected = WorkforceValidationException.class)
    public void Test_getOptimumSolution_Expection()
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{24,28});
        request.setSenior(1);
        request.setJunior(6);
    
        Errors errors = new BeanPropertyBindingResult(request, "request");
    
        workforceController.getOptimumSolution(request,errors);
    }
	
	/**
	 * Test get optimum solution expection 2.
	 */
	@Test(expected = WorkforceValidationException.class)
    public void Test_getOptimumSolution_Expection2()
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{124});
        request.setSenior(10);
        request.setJunior(6);
        
        Errors errors = new BeanPropertyBindingResult(request, "request");
        
        workforceController.getOptimumSolution(request,errors);
    }
    
    
}
