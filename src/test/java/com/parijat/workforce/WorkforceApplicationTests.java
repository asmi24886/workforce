package com.parijat.workforce;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The type Workforce application tests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WorkforceApplicationTests
{
	/**
	 * Context loads.
	 */
	@Test
	public void contextLoads()
	{
		// THE METHOD WILL REMAIN EMPTY
	}
	
	/**
	 * Main application test.
	 */
	@Test
	public void MainApplicationTest()
	{
		WorkforceApplication.main(new String[]{});
	}
}
