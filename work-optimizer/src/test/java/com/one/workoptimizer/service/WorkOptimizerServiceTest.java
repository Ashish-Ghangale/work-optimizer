package com.one.workoptimizer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.one.workoptimizer.data.OptimizedResult;
import com.one.workoptimizer.data.Resource;
import com.one.workoptimizer.exception.InvalidInputException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkOptimizerServiceTest {

	@Autowired
	WorkOptimizerService workOptimizerService;

	@MockBean
	@Qualifier("Total")
	WorkForceManager workforceManager;

	@MockBean
	@Qualifier("Senior")
	SeniorWorkforceManager seniorWorkforceManager;

	@MockBean
	@Qualifier("Junior")
	JuniorWorkforceManager juniorWorkforceManager;

	@Test
	public void givenResource_whenProcessInput_thenReturnOptimizedResult() throws InvalidInputException {
		Resource resource = new Resource();
		resource.setRooms(Arrays.asList(15, 25));
		resource.setSenior(50);
		resource.setJunior(40);

		List<Map<String, Integer>> expectedResultList = new ArrayList<Map<String, Integer>>();
		Map<String, Integer> resultMap1 = new HashMap<String, Integer>();
		resultMap1.put("senior", 1);
		resultMap1.put("junior", 1);
		Map<String, Integer> resultMap2 = new HashMap<String, Integer>();
		resultMap2.put("senior", 2);
		resultMap2.put("junior", 1);
		expectedResultList.add(resultMap1);
		expectedResultList.add(resultMap2);

		Mockito.when(workforceManager.getOptimizedWorkforce(Mockito.any(), Mockito.anyInt(), Mockito.anyDouble()))
				.thenReturn(Arrays.asList(2, 3));
		Mockito.when(seniorWorkforceManager.getOptimizedWorkforce(Mockito.any(), Mockito.anyInt(), Mockito.anyDouble()))
				.thenReturn(Arrays.asList(1, 2));
		Mockito.when(seniorWorkforceManager.verifyAndAdjustSeniorWorkforce(Mockito.any(), Mockito.anyInt()))
				.thenReturn(Arrays.asList(1, 2));
		Mockito.when(juniorWorkforceManager.generateDemandForJuniorWorker(Mockito.any(), Mockito.any()))
				.thenReturn(Arrays.asList(1, 1));
		Mockito.when(juniorWorkforceManager.getOptimizedWorkforce(Mockito.any(), Mockito.anyInt()))
				.thenReturn(Arrays.asList(1, 1));
		Mockito.when(juniorWorkforceManager.verifyAndAdjustJuniorWorkforce(Mockito.any(), Mockito.anyInt()))
				.thenReturn(Arrays.asList(1, 1));

		OptimizedResult optimizedResult = workOptimizerService.processInput(resource);
		assertThat(optimizedResult.getResultList()).isEqualTo(expectedResultList);
	}

	@Test(expected = InvalidInputException.class)
	public void givenInvalidResource_whenProcessInput_thenReturnInvalidInputException() throws InvalidInputException {
		Resource resource = new Resource();
		resource.setRooms(Arrays.asList(15, 25));
		resource.setSenior(0);
		resource.setJunior(40);

		workOptimizerService.processInput(resource);

	}

}
