package comp3011assignment1.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import comp3011assignment1.dto.GlobalStatsResponse;
import comp3011assignment1.service.GlobalStatsService;

/**
 * Regression tests for the global statistic endpoint.
 * 
 * These tests verify that the controller returns the correct HTTP
 * status and JSOn response containing the input and output tokens.
 * 
 * GlobalStatService is mocked so the controller can be tested independently.
 */

@WebMvcTest(GlobalController.class)
public class GlobalControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private GlobalStatsService globalStatsService;
	
	/**
	 * Verifies that the global stats endpoint correctly returns
	 * zero token usage when no requests have been made. 
	 */
	
	@Test
	void getGlobalStatsReturnsZeroFirstTime() throws Exception {
		when(globalStatsService.getGlobalStats()).thenReturn(new GlobalStatsResponse(0L, 0L));
		
		mockMvc.perform(get("/api/v1/global/stats"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.inputTokens").value(0))
			.andExpect(jsonPath("$.outputTokens").value(0));
	}
	
	/**
	 * Verifies that the endpoint correctly returns known input and output
	 * tokens from the stats service.
	 */
	
	@Test
	void getGlobalStatsReturnsAfterTokensUpdated() throws Exception {
		when(globalStatsService.getGlobalStats()).thenReturn(new GlobalStatsResponse(100L, 200L));
		
		mockMvc.perform(get("/api/v1/global/stats"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.inputTokens").value(100))
			.andExpect(jsonPath("$.outputTokens").value(200));
	}

}
