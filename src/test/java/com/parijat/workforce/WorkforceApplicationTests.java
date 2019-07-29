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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WorkforceApplicationTests
{
	@Test
	public void contextLoads()
	{
	}

	@Test
	public void MainApplicationTest()
	{
		WorkforceApplication.main(new String[]{});
	}
}
