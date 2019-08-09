package com.one.workoptimizer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WorkOptimizerITTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenResourceInput_whenOptimizeWorkforce_thenReturnOptimizedSeniorJuniorWorkersJson() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/work-optimizer/v1/optimize")
				.content("{ \"rooms\": [90, 10, 50], \"senior\": 9, \"junior\": 5 }")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(
				"{\"resultList\":[{\"senior\":4,\"junior\":3},{\"senior\":1,\"junior\":0},{\"senior\":3,\"junior\":2}]}",
				result.getResponse().getContentAsString(), JSONCompareMode.STRICT);
	}

	@Test
	public void givenInvalidResourceInput_whenOptimizeWorkforce_thenReturnErrorMessage() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/work-optimizer/v1/optimize")
				.content("{ \"rooms\": [90, 10, 50], \"senior\": 0, \"junior\": 5 }")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(400, result.getResponse().getStatus());
	}
}
