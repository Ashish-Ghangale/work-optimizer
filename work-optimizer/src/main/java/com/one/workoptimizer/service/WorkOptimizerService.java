package com.one.workoptimizer.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.one.workoptimizer.data.OptimizedResult;
import com.one.workoptimizer.data.Resource;
import com.one.workoptimizer.exception.InvalidInputException;

/**
 * 
 * @author Ashish G
 *
 */
@Service
public class WorkOptimizerService {
	private static final Logger LOGGER = Logger.getLogger(WorkOptimizerService.class.getName());

	@Autowired
	@Qualifier("Total")
	WorkForceManager workforceManager;

	@Autowired
	@Qualifier("Senior")
	SeniorWorkforceManager seniorWorkforceManager;

	@Autowired
	@Qualifier("Junior")
	JuniorWorkforceManager juniorWorkforceManager;

	/**
	 * 
	 * @param resource
	 * @return
	 * @throws InvalidInputException
	 */
	public OptimizedResult processInput(Resource resource) throws InvalidInputException {
		validateInput(resource);
		OptimizedResult optimizedResult = determineOptimumWorkforce(resource);
		return optimizedResult;

	}

	private OptimizedResult determineOptimumWorkforce(Resource resource) {
		return optimize(resource);
	}

	private OptimizedResult optimize(Resource resource) {
		int totalAvailableWorkforce = resource.getSenior() + resource.getJunior();
		List<Integer> optimizedWorkforce = workforceManager.getOptimizedWorkforce(resource.getRooms(),
				totalAvailableWorkforce, WorkForceManager.DIVISION_FACTOR_WORKERS_EVERY_STRUCTURE);
		LOGGER.info("Assigned workforce per structure: " + optimizedWorkforce);

		List<Integer> optimizedSeniorWorkforce = seniorWorkforceManager.getOptimizedWorkforce(optimizedWorkforce,
				resource.getSenior(), SeniorWorkforceManager.DIVISION_FACTOR_SENIOR_WORKER_EVERY_STRUCTURE);
		optimizedSeniorWorkforce = seniorWorkforceManager.verifyAndAdjustSeniorWorkforce(optimizedSeniorWorkforce,
				resource.getSenior());
		LOGGER.info("Assigned Seniors per structure: " + optimizedSeniorWorkforce);

		List<Integer> juniorDemandWorkforce = juniorWorkforceManager
				.generateDemandForJuniorWorker(optimizedSeniorWorkforce, optimizedWorkforce);
		List<Integer> optimizedJuniorWorkforce = juniorWorkforceManager.getOptimizedWorkforce(juniorDemandWorkforce,
				resource.getJunior());
		optimizedJuniorWorkforce = juniorWorkforceManager.verifyAndAdjustJuniorWorkforce(optimizedJuniorWorkforce,
				resource.getJunior());
		LOGGER.info("Assigned Juniors per structure: " + optimizedJuniorWorkforce);

		List<Map<String, Integer>> resultList = new ArrayList<Map<String, Integer>>();
		AtomicInteger index = new AtomicInteger(0);
		for (int structure : resource.getRooms()) {
			Map<String, Integer> resultMap = new LinkedHashMap<String, Integer>();
			resultMap.put("senior", optimizedSeniorWorkforce.get(index.get()));
			resultMap.put("junior", optimizedJuniorWorkforce.get(index.getAndIncrement()));
			resultList.add(resultMap);
		}
		;
		LOGGER.info("Optimised Output Workforce: " + resultList);
		return new OptimizedResult(resultList);
	}

	private void validateInput(Resource resource) throws InvalidInputException {
		if (resource.getRooms() == null || resource.getSenior() < resource.getRooms().size()
				|| resource.getRooms().size() > 100 || resource.getRooms().contains(0)) {
			throw new InvalidInputException(
					"Invalid Input: The number of Seniors cannot be less than the number of Structures or "
					+ "the rooms cannot be 0 or "
					+ "the structures cannot be more than 100");
		}

	}

}
