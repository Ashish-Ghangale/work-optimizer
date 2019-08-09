package com.one.workoptimizer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Ashish G
 *
 */
@Component("Total")
public class WorkForceManager {

	public static final double DIVISION_FACTOR_WORKERS_EVERY_STRUCTURE = 10.0;
	public static final int REDUCE_DEMAND_BY_FACTOR = 2;

	/**
	 * 
	 * @param input
	 * @param totalAvailableWorkforce
	 * @param factor
	 * @return
	 */
	protected List<Integer> getOptimizedWorkforce(List<Integer> input, int totalAvailableWorkforce, double factor) {
		List<Integer> workforceDemand = input.stream().map(i -> {
			double division = i / factor;
			return (int) Math.ceil(division);
		}).collect(Collectors.toList());

		List<Integer> requestedWorkforceDemand = workforceDemand;
		int totalWorkforceDemand = workforceDemand.stream().reduce(0, Integer::sum);

		if (totalAvailableWorkforce - totalWorkforceDemand >= 0) {
			return workforceDemand;
		} else {
			List<Integer> reducedWorkforceDemand = reduceWorkforceDemand(totalAvailableWorkforce, workforceDemand,
					requestedWorkforceDemand);
			return reducedWorkforceDemand;
		}
	}

	/**
	 * 
	 * @param totalAvailableWorkforce
	 * @param workforceDemand
	 * @param requestedWorkforceDemand
	 * @return
	 */
	protected List<Integer> reduceWorkforceDemand(int totalAvailableWorkforce, List<Integer> workforceDemand,
			List<Integer> requestedWorkforceDemand) {
		AtomicBoolean maxReductionAchieved = new AtomicBoolean(true);
		List<Integer> finalReducedWorkforceDemand = null;
		AtomicInteger copyOfTotalAvailableWorkforce = new AtomicInteger(totalAvailableWorkforce);
		while (copyOfTotalAvailableWorkforce.get() != 0) {
			maxReductionAchieved.set(true);
			List<Integer> reducedWorkforceDemand = workforceDemand.stream().map(i -> {
				if (i == 1) {
					return i;
				} else {
					maxReductionAchieved.set(false);
					double division = i / REDUCE_DEMAND_BY_FACTOR;
					return (int) Math.ceil(division);
				}
			}).collect(Collectors.toList());
			if(maxReductionAchieved.get()) {
				finalReducedWorkforceDemand = reducedWorkforceDemand;
				break;
			}
			int totalReducedWorkforceDemand = reducedWorkforceDemand.stream().reduce(0, Integer::sum);
			AtomicInteger workForceCapacity = new AtomicInteger(totalAvailableWorkforce - totalReducedWorkforceDemand);
			if (workForceCapacity.get() > 0) {
				while (workForceCapacity.get() > 0) {
					List<Integer> copyOfReducedWorkforceDemand = new ArrayList<Integer>();
					AtomicInteger index = new AtomicInteger(0);
					reducedWorkforceDemand.forEach(demand -> {
						if (demand < requestedWorkforceDemand.get(index.get())) {
							demand++;
							workForceCapacity.decrementAndGet();
						}
						copyOfReducedWorkforceDemand.add(index.getAndIncrement(), demand);
					});
					reducedWorkforceDemand = copyOfReducedWorkforceDemand;
					if ((int) reducedWorkforceDemand.stream().reduce(0, Integer::sum) == totalAvailableWorkforce) {
						finalReducedWorkforceDemand = reducedWorkforceDemand;
						break;
					}
				}
				finalReducedWorkforceDemand = reducedWorkforceDemand;
				copyOfTotalAvailableWorkforce.decrementAndGet();
			} else if (workForceCapacity.get() == 0) {
				finalReducedWorkforceDemand = reducedWorkforceDemand;
			} else {
				workforceDemand = reducedWorkforceDemand;
				continue;
			}
			break;
		}
		return finalReducedWorkforceDemand;
	}

}
