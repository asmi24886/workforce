package com.parijat.workforce;

import com.parijat.workforce.controller.WorkforceController;
import com.parijat.workforce.model.CleanerSet;
import com.parijat.workforce.model.RequestModel;
import com.parijat.workforce.processor.IOptimizationProcessor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * The type Workforce controller rest api tests.
 */
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
	
	private static final String ERROR_NAME = "ERROR";
	private static final String ERROR_MESSAGE = "SAMPLE ERROR MESSAGE";
	private static final String REQUEST = "request";
	
	/**
	 * Test get optimum solution api sample 1.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPISample1Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{35, 21, 17, 28});
        request.setSenior(10);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, REQUEST);

        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(prepareResultData1(), HttpStatus.OK));

        String json = "{ \"rooms\": [35, 21, 17, 28], \"senior\": 10, \"junior\": 6 }";
        callRestAPItoOptimize(json);
    }
	
	/**
	 * Test get optimum solution api sample 2.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPISample2Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{24,28});
        request.setSenior(11);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, REQUEST);

        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(prepareResultData2(), HttpStatus.OK));

        String json = "{ \"rooms\": [24,28], \"senior\": 11, \"junior\": 6 }";
        callRestAPItoOptimize(json);
    }
	
	/**
	 * Test get optimum solution api sample 3.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPISample3Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{0});
        request.setSenior(11);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, REQUEST);

        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(prepareResultData3(), HttpStatus.OK));

        String json = "{ \"rooms\": [0], \"senior\": 11, \"junior\": 6 }";
        callRestAPItoOptimize(json);
    }
	
	/**
	 * Test get optimum solution api sample 4.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPISample4Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{110});
        request.setSenior(11);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, REQUEST);

        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(prepareResultData3(), HttpStatus.OK));

        String json = "{ \"rooms\": [110], \"senior\": 11, \"junior\": 6 }";
        callRestAPItoOptimize(json);
    }
	
	/**
	 * Test get optimum solution api sample 5.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPISample5Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{11});
        request.setSenior(10);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, REQUEST);

        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(prepareResultData4(), HttpStatus.OK));

        String json = "{ \"rooms\": [11], \"senior\": 10, \"junior\": 6 }";
        callRestAPItoOptimize(json);
    }
	
	/**
	 * Test get optimum solution api sample 6.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPISample6Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{-10});
        request.setSenior(11);
        request.setJunior(6);

        Errors errors = new BeanPropertyBindingResult(request, REQUEST);

        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(prepareResultData3(), HttpStatus.OK));

        String json = "{ \"rooms\": [-10], \"senior\": 11, \"junior\": 6 }";
        callRestAPItoOptimize(json);
    }
	
	/**
	 * Test get optimum solution api unprocessable entity 1.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPIUnprocessableEntity1Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(null);
        request.setSenior(11);
        request.setJunior(6);
    
        Errors errors = new BeanPropertyBindingResult(request, REQUEST);
    
        given(controller.getOptimumSolution(request,errors)).willReturn(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    
        String json = "{ \"rooms\": null, \"senior\": 11, \"junior\": 6 }";
        callRestAPItoOptimize(json);
        
    }
	
	/**
	 * Test get optimum solution api unprocessable entity 2.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPIUnprocessableEntity2Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[] {});
        request.setSenior(11);
        request.setJunior(6);
    
        Errors errors = new BeanPropertyBindingResult(request, REQUEST);
    
        given(controller.getOptimumSolution(request, errors)).willReturn(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    
        String json = "{ \"rooms\": [], \"senior\": 11, \"junior\": 6 }";
        callRestAPItoOptimize(json);
    }
	
	/**
	 * Test get optimum solution api unprocessable entity 3.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void getOptimumSolutionAPIUnprocessableEntity3Test() throws Exception
    {
        RequestModel request = new RequestModel();
        request.setRooms(new Integer[]{35});
        request.setSenior(null);
        request.setJunior(null);
        
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(ERROR_NAME,ERROR_NAME);
        result.addError(new ObjectError(ERROR_NAME,ERROR_MESSAGE));
        
        given(controller.getOptimumSolution(request, result)).willReturn(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
        
        String json = "{ \"rooms\": [35], \"senior\": null, \"junior\": null }";
        callRestAPItoOptimize(json);
    }

    private void callRestAPItoOptimize(String json) throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/workforce/optimize")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    private List<CleanerSet> prepareResultData1()
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

    private List<CleanerSet> prepareResultData2()
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

    private List<CleanerSet> prepareResultData3()
    {
        List<CleanerSet> expected = new ArrayList<>();
        CleanerSet cleanerSet = new CleanerSet();
        cleanerSet.setSenior(0);
        cleanerSet.setJunior(0);
        expected.add(cleanerSet);

        return expected;
    }

    private List<CleanerSet> prepareResultData4()
    {
        List<CleanerSet> expected = new ArrayList<>();
        CleanerSet cleanerSet = new CleanerSet();
        cleanerSet.setSenior(1);
        cleanerSet.setJunior(0);
        expected.add(cleanerSet);

        return expected;
    }
}
