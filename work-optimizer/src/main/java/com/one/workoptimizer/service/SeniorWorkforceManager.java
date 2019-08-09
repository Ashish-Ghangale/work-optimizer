package com.one.workoptimizer.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Ashish G
 *
 */
@Component("Senior")
public class SeniorWorkforceManager extends WorkForceManager {
	public static final double DIVISION_FACTOR_SENIOR_WORKER_EVERY_STRUCTURE = 2.0;

	
	@Override
	public List<Integer> getOptimizedWorkforce(List<Integer> input, int totalAvailableWorkforce, double factor) {
		return super.getOptimizedWorkforce(input, totalAvailableWorkforce, factor);
	}

	@Override
	public List<Integer> reduceWorkforceDemand(int totalAvailableWorkforce, List<Integer> workforceDemand,
			List<Integer> requestedWorkforceDemand) {
		return super.reduceWorkforceDemand(totalAvailableWorkforce, workforceDemand, requestedWorkforceDemand);
	}

	/**
	 * 
	 * @param optimizedSeniorWorkforce
	 * @param senior
	 * @return
	 */
	public List<Integer> verifyAndAdjustSeniorWorkforce(List<Integer> optimizedSeniorWorkforce, int senior) {
		int seniorWorkforceDemand = optimizedSeniorWorkforce.stream().reduce(0, Integer::sum);
		int difference = senior - seniorWorkforceDemand;
		AtomicInteger index = new AtomicInteger(0);
		if (difference < 0) {
			while (difference <= 0 && index.get() < optimizedSeniorWorkforce.size()) {
				if (optimizedSeniorWorkforce.get(index.get()) > 1) {
					optimizedSeniorWorkforce.set(index.get(), optimizedSeniorWorkforce.get(index.get()) - 1);
					difference++;
				}
				index.getAndIncrement();
			}
		}
		return optimizedSeniorWorkforce;
	}

}
