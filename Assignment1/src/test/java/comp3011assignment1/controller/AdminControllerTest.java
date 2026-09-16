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
import comp3011assignment1.dto.ErrorResponse;
import comp3011assignment1.dto.ShutdownResponse;
import comp3011assignment1.service.ShutdownService;

/**
 * Regression tests for the admin API endpoints.
 * 
 * These tests verify that the uptime and shutdown endpoints return
 * the expected HTTP statues and responses.
 * 
 * The services are mocked to test the controller independently.
 */

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
	
	/**
	 * Verifies that the uptime endpoint returns the server start time,
	 * current time and calculated uptime.
	 */
	
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
	
	/**
	 * Verifies that a valid shutdown request is returns 202
	 * and the expected shutdown message.
	 */
	
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
	
	/**
	 * Verifies that a valid shutdown request returns 409
	 * and the shutdown conflict message.
	 */
	
	@Test
	void shutdownServerReturnsConflict() throws Exception {
		
		doReturn(ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(Instant.parse("2026-09-16T00:00:00Z"),
						409,
						"Conflict",
						"Graceful shutdown is already in progress.",
						"/api/v1/admin/shutdown"))
				).when(shutdownService).shutdown();
		
		mockMvc.perform(post("/api/v1/admin/shutdown"))
		.andExpect(status().isConflict())
		.andExpect(jsonPath("$.status").value(409))
		.andExpect(jsonPath("$.error").value("Conflict"))
		.andExpect(jsonPath("$.message").value("Graceful shutdown is already in progress."))
		.andExpect(jsonPath("$.path").value("/api/v1/admin/shutdown"));
	}


}
