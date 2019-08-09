/**
 * 
 */
package com.one.workoptimizer.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.one.workoptimizer.data.OptimizedResult;
import com.one.workoptimizer.data.Resource;
import com.one.workoptimizer.exception.InvalidInputException;
import com.one.workoptimizer.service.WorkOptimizerService;

/**
 * @author Ashish G 
 * REST Controller with endpoint to return optimized workforce for a given structure
 */
@RestController
@RequestMapping("/api/work-optimizer")
public class WorkOptimizerController {
	private static final Logger LOGGER = Logger.getLogger(WorkOptimizerController.class.getName());

	@Autowired
	WorkOptimizerService workOptimizerService;

	/**
	 * 
	 * @param resource
	 * @return ResponseEntity<OptimizedResult>-List of Maps containing the optimal number of Juniors and Seniors for every structure 
	 * @throws InvalidInputException
	 */
	@PostMapping("/v1/optimize")
	public ResponseEntity<OptimizedResult> optimizeWorkforce(@RequestBody Resource resource) throws InvalidInputException {
		LOGGER.info("--Start--: Optimizing solution for Input: " + resource);
		OptimizedResult optimizedResult = workOptimizerService.processInput(resource);
		return ResponseEntity.status(HttpStatus.OK).body(optimizedResult);
	}

}
