package com.one.workoptimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.one.workoptimizer.data.OptimizedResult;
import com.one.workoptimizer.service.WorkOptimizerService;

/**
 * 
 * @author Ashish G
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class WorkOptimizerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	WorkOptimizerService workOptimizerService;

	@Test
	public void givenResourceInput_whenOptimizeWorkforce_thenReturnOptimizedSeniorJuniorWorkers() throws Exception {

		List<Map<String, Integer>> resultList = new ArrayList<Map<String, Integer>>();
		Map<String, Integer> resultMap1 = new HashMap<String, Integer>();
		resultMap1.put("senior", 4);
		resultMap1.put("junior", 3);
		Map<String, Integer> resultMap2 = new HashMap<String, Integer>();
		resultMap2.put("senior", 1);
		resultMap2.put("junior", 0);
		Map<String, Integer> resultMap3 = new HashMap<String, Integer>();
		resultMap3.put("senior", 3);
		resultMap3.put("junior", 2);
		resultList.add(resultMap1);
		resultList.add(resultMap2);
		resultList.add(resultMap3);
		OptimizedResult optimizedResult = new OptimizedResult(resultList);

		Mockito.when(workOptimizerService.processInput(Mockito.any())).thenReturn(optimizedResult);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/work-optimizer/v1/optimize")
				.content("{ \"rooms\": [90, 10, 50], \"senior\": 9, \"junior\": 5 }")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(
				"{\"resultList\":[{\"senior\":4,\"junior\":3},{\"senior\":1,\"junior\":0},{\"senior\":3,\"junior\":2}]}",
				result.getResponse().getContentAsString(), JSONCompareMode.STRICT);
	}

}
