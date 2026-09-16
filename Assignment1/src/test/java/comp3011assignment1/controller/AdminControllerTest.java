package comp3011assignment1.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import comp3011assignment1.dto.UptimeResponse;
import comp3011assignment1.service.UptimeService;
import comp3011assignment1.dto.ShutdownResponse;
import comp3011assignment1.service.ShutdownService;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private UptimeService uptimeService;
	
	@MockitoBean
	private ShutdownService shutdownService;
	
	@MockitoBean
	private ConfigurableApplicationContext context;
	
	@Test
	void uptimeReturnsCorrectUptime() throws Exception {
		
		Instant start = Instant.parse("2026-09-16T00:00:00Z");
		Instant now = Instant.parse("2026-09-16T00:01:30Z");
		
		when(uptimeService.getUptime()).thenReturn(new UptimeResponse(start, now, 90.0));
		
		mockMvc.perform(get("/api/v1/admin/uptime"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.utcServerStart").value("2026-09-16T00:00:00Z"))
			.andExpect(jsonPath("$.utcNow").value("2026-09-16T00:01:30Z"))
			.andExpect(jsonPath("$.serverUptimeSeconds").value(90.0));
	}
	
	@Test
	void shutdownServerReturnsAccepted() throws Exception {
		
		doReturn(ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(new ShutdownResponse("Graceful shutdown requested."))
		).when(shutdownService).shutdown();
		
		mockMvc.perform(post("/api/v1/admin/shutdown"))
			.andExpect(status().isAccepted())
			.andExpect(jsonPath("$.message").value("Graceful shutdown requested."));
		
	}
	
	@Test
	void shutdownServerReturnsConflict() throws Exception {
		
	}


}
