package com.parijat.workforce;

import com.parijat.workforce.controller.WorkforceController;
import com.parijat.workforce.model.CleanerSet;
import com.parijat.workforce.model.RequestModel;
import com.parijat.workforce.processor.IOptimizationProcessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Slf4j
@WebMvcTest(value = WorkforceController.class)
public class WorkforceControllerRestAPITests
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkforceController controller;

    @Autowired
    private WorkforceController workforceController;

    @MockBean
    private IOptimizationProcessor optimizationProcessor;

    private List<CleanerSet> prepareTestData1()
    {
        List<CleanerSet> expected = new ArrayList<>();
        CleanerSet cleanerSet = new CleanerSet();
        cleanerSet.setSenior(3);
        cleanerSet.setJunior(1);
        expected.add(cleanerSet);

        cleanerSet = new CleanerSet();
        cleanerSet.setSenior(1);
        cleanerSet.setJunior(2);
        expected.add(cleanerSet);

        cleanerSet = new CleanerSet();
        cleanerSet.setSenior(2);
        cleanerSet.setJunior(0);
        expected.add(cleanerSet);

        cleanerSet = new CleanerSet();
        cleanerSet.setSenior(1);
        cleanerSet.setJunior(3);
        expected.add(cleanerSet);

        return expected;
    }

    private List<CleanerSet> prepareTestData2()
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
    public void Test_getOptimumSolutionAPI_Sample1() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{35, 21, 17, 28});
        request.setSenior(10);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, "request");

        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(prepareTestData1(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/workforce/optimize")
                .content("{ \"rooms\": [35, 21, 17, 28], \"senior\": 10, \"junior\": 6 }")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void Test_getOptimumSolutionAPI_Sample2() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{24,28});
        request.setSenior(11);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, "request");

        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(prepareTestData2(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/workforce/optimize")
                .content("{ \"rooms\": [24,28], \"senior\": 11, \"junior\": 6 }")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
