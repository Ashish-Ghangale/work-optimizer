package com.one.workoptimizer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Ashish G
 *
 */
@Component("Junior")
public class JuniorWorkforceManager extends WorkForceManager {

	/**
	 * 
	 * @param juniorDemandWorkforce
	 * @param totalAvailableWorkforce
	 * @return List<Integer>
	 */
	public List<Integer> getOptimizedWorkforce(List<Integer> juniorDemandWorkforce, int totalAvailableWorkforce) {
		List<Integer> requestedWorkforceDemand = juniorDemandWorkforce;
		int totalWorkforceDemand = juniorDemandWorkforce.stream().reduce(0, Integer::sum);

		if (totalAvailableWorkforce - totalWorkforceDemand >= 0) {
			return juniorDemandWorkforce;
		} else {
			List<Integer> reducedWorkforceDemand = reduceWorkforceDemand(totalAvailableWorkforce, juniorDemandWorkforce,
					requestedWorkforceDemand);
			return reducedWorkforceDemand;
		}
	}

	
	@Override
	public List<Integer> reduceWorkforceDemand(int totalAvailableWorkforce, List<Integer> workforceDemand,
			List<Integer> requestedWorkforceDemand) {
		return super.reduceWorkforceDemand(totalAvailableWorkforce, workforceDemand, requestedWorkforceDemand);
	}

	/**
	 * 
	 * @param optimizedSeniorWorkforce
	 * @param optimizedWorkforce
	 * @return List<Integer>
	 */
	public List<Integer> generateDemandForJuniorWorker(List<Integer> optimizedSeniorWorkforce,
			List<Integer> optimizedWorkforce) {
		List<Integer> juniorDemandWorkforce = new ArrayList<Integer>();
		AtomicInteger loopCount = new AtomicInteger(optimizedSeniorWorkforce.size());
		AtomicInteger index = new AtomicInteger(0);
		while (loopCount.get() > 0) {
			juniorDemandWorkforce.add(index.get(),
					(optimizedWorkforce.get(index.get()) - optimizedSeniorWorkforce.get(index.get())));
			index.incrementAndGet();
			loopCount.decrementAndGet();
		}
		return juniorDemandWorkforce;
	}
	
	public List<Integer> verifyAndAdjustJuniorWorkforce(List<Integer> optimizedJuniorWorkforce, int junior) {
		int juniorWorkforceDemand = optimizedJuniorWorkforce.stream().reduce(0, Integer::sum);
		int difference = junior - juniorWorkforceDemand;
		AtomicInteger index = new AtomicInteger(0);
		if (difference < 0) {
			while (difference <= 0 && index.get() < optimizedJuniorWorkforce.size()) {
				if (optimizedJuniorWorkforce.get(index.get()) > 0) {
					optimizedJuniorWorkforce.set(index.get(), optimizedJuniorWorkforce.get(index.get()) - 1);
					difference++;
				}
				index.getAndIncrement();
			}
		}
		return optimizedJuniorWorkforce;
	}

}
