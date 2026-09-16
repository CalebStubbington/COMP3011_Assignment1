package comp3011assignment1.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import comp3011assignment1.service.GlobalStatsService;

public class TranscriptionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private GlobalStatsService globalStatsService;
	
	AtomicLong expectedValueTest1 = new AtomicLong(0L);
	AtomicLong expectedValueTest2 = new AtomicLong(100L);
	
	@Test
	void getGlobalStatsReturnsZeroFirstTime() throws Exception {
		when(globalStatsService.getInputTokens()).thenReturn(expectedValueTest1);
		when(globalStatsService.getOutputTokens()).thenReturn(expectedValueTest1);
		
		mockMvc.perform(get("/api/v1/global/stats"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.inputTokens").value(0))
			.andExpect(jsonPath("$.outputTokens").value(0));
	}
	
	@Test
	void getGlobalStatsReturnsAfterTokensUpdated() throws Exception {
		when(globalStatsService.getInputTokens()).thenReturn(expectedValueTest2);
		when(globalStatsService.getOutputTokens()).thenReturn(expectedValueTest2);
		
		mockMvc.perform(get("/api/v1/global/stats"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.inputTokens").value(100))
			.andExpect(jsonPath("$.outputTokens").value(100));
	}
}
