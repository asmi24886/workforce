package com.parijat.workforce;

import com.parijat.workforce.model.CleanerSet;
import com.parijat.workforce.processor.OptimizationProcessorImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WorkforceApplicationTests {

	@Autowired
	private OptimizationProcessorImpl optimizationProcessor;

	@Test
	public void contextLoads()
	{
	}

	@Test
	public void Test_getMaximumCleanerCount()
	{
		log.info("Testing Non-Public getMaximumCleanerCount()...");
		Integer actual = ReflectionTestUtils.invokeMethod(optimizationProcessor,"getMaximumCleanerCount",
				25,10);
		Integer expected = 3;
		log.info("Actual : "+actual+", expected : "+expected);
		Assert.assertEquals(expected,actual);
	}

	@Test
	public void Test_findLowestCapacity()
	{
		log.info("Testing Non-Public findOptimumCombination()...");
		CleanerSet actual =
				ReflectionTestUtils.invokeMethod(optimizationProcessor, "findOptimumCombination",
						25,3,2,10,6);
		CleanerSet expected = new CleanerSet();
		expected.setSenior(2);
		expected.setJunior(1);
		log.info("Actual : "+actual+", expected : "+expected);
		Assert.assertEquals(expected,actual);
	}
}
