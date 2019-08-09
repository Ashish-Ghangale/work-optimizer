package com.one.workoptimizer.data;

import java.util.Map;
import java.util.List;

/**
 * @author Ashish G
 */
public class OptimizedResult {
	private final List<Map<String, Integer>> resultList;
	
	public OptimizedResult(List<Map<String, Integer>> resultList) {
		this.resultList = resultList;
	}

	public List<Map<String, Integer>> getResultList() {
		return resultList;
	}	
}
