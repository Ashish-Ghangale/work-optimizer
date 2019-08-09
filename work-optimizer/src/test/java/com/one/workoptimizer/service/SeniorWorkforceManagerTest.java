package com.one.workoptimizer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.one.workoptimizer.exception.InvalidInputException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeniorWorkforceManagerTest {

	@Autowired
	@Qualifier("Senior")
	SeniorWorkforceManager seniorWorkforceManager;

	@Test
	public void givenResourceInput_whenGetOptimizedWorkforce_thenReturnOptimalWorkforce() throws InvalidInputException {

		List<Integer> input = Arrays.asList(15, 25);
		int totalAvailableWorkforce = 90;
		double factor = 10.0;

		List<Integer> result = seniorWorkforceManager.getOptimizedWorkforce(input, totalAvailableWorkforce, factor);
		assertThat(result).isEqualTo(Arrays.asList(2, 3));

	}

	@Test
	public void givenResourceInput2_whenGetOptimizedWorkforce_thenReturnOptimalWorkforce()
			throws InvalidInputException {

		List<Integer> input = Arrays.asList(95, 85);
		int totalAvailableWorkforce = 2;
		double factor = 10.0;

		List<Integer> result = seniorWorkforceManager.getOptimizedWorkforce(input, totalAvailableWorkforce, factor);
		assertThat(result).isEqualTo(Arrays.asList(1, 1));

	}

	@Test
	public void givenResourceInput_whenReduceWorkforceDemand_thenReturnReducedWorkforceDemand()
			throws InvalidInputException {

		List<Integer> workforceDemand = Arrays.asList(5, 4, 3, 2);
		List<Integer> requestedWorkforceDemand = Arrays.asList(9, 8, 5, 4);
		int totalAvailableWorkforce = 5;

		List<Integer> result = seniorWorkforceManager.reduceWorkforceDemand(totalAvailableWorkforce, workforceDemand,
				requestedWorkforceDemand);
		assertThat(result).isEqualTo(Arrays.asList(2, 2, 2, 2));

	}

	@Test
	public void givenOptimizedSeniorWorkforce_whenVerifyAndAdjustSeniorWorkforce_thenReturnOptimizedSeniorWorkforce()
			throws InvalidInputException {

		List<Integer> optimizedSeniorWorkforce = Arrays.asList(2, 2);
		int senior = 2;

		List<Integer> result = seniorWorkforceManager.verifyAndAdjustSeniorWorkforce(optimizedSeniorWorkforce, senior);
		assertThat(result).isEqualTo(Arrays.asList(1, 1));

	}

}
