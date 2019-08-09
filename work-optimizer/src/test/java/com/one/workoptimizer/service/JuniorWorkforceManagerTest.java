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
public class JuniorWorkforceManagerTest {

	@Autowired
	@Qualifier("Junior")
	JuniorWorkforceManager juniorWorkforceManager;

	@Test
	public void givenDemandWorkforce_whenGetOptimizedWorkforce_thenReturnOptimalWorkforce()
			throws InvalidInputException {

		List<Integer> input = Arrays.asList(5, 2);
		int junior = 5;

		List<Integer> result = juniorWorkforceManager.getOptimizedWorkforce(input, junior);
		assertThat(result).isEqualTo(Arrays.asList(3, 2));

	}

	@Test
	public void givenWorforceDemand_whenReduceWorkforceDemand_thenReturnReducedWorkforceDemand()
			throws InvalidInputException {

		List<Integer> workforceDemand = Arrays.asList(5, 4, 3, 2);
		List<Integer> requestedWorkforceDemand = Arrays.asList(9, 8, 5, 4);
		int totalAvailableWorkforce = 5;

		List<Integer> result = juniorWorkforceManager.reduceWorkforceDemand(totalAvailableWorkforce, workforceDemand,
				requestedWorkforceDemand);
		assertThat(result).isEqualTo(Arrays.asList(2, 2, 2, 2));

	}

	@Test
	public void givenOptimizedJuniorWorkforce_whenVerifyAndAdjustJuniorWorkforce_thenReturnOptimizedJuniorWorkforce()
			throws InvalidInputException {

		List<Integer> optimizedJuniorWorkforce = Arrays.asList(2, 2);
		int junior = 2;

		List<Integer> result = juniorWorkforceManager.verifyAndAdjustJuniorWorkforce(optimizedJuniorWorkforce, junior);
		assertThat(result).isEqualTo(Arrays.asList(1, 1));

	}

	@Test
	public void givenOptimizedSeniorWorkforce_whenGenerateDemandForJuniorWorker_thenReturnJuniorWorkforceDemand()
			throws InvalidInputException {

		List<Integer> optimizedSeniorWorkforce = Arrays.asList(3, 3);
		List<Integer> optimizedWorkforce = Arrays.asList(5, 5);

		List<Integer> result = juniorWorkforceManager.generateDemandForJuniorWorker(optimizedSeniorWorkforce,
				optimizedWorkforce);
		assertThat(result).isEqualTo(Arrays.asList(2, 2));

	}

}
