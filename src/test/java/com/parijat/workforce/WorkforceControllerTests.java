package com.parijat.workforce;

import com.parijat.workforce.controller.WorkforceController;
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
}
