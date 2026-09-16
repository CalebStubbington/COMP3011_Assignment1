package comp3011assignment1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comp3011assignment1.service.GlobalStatsService;
import comp3011assignment1.dto.GlobalStatsResponse;

/**
 * REST controller that provides global app statistics.
 * 
 * Reveals the total input and output token usage since the start of the server.
 */

@RestController
@RequestMapping("/api/v1/global")
public class GlobalController {
	
	private final GlobalStatsService globalStatsService;
	
	public GlobalController(GlobalStatsService globalStatsService) {
		this.globalStatsService = globalStatsService;
	}
	
	@GetMapping("/stats")
	public GlobalStatsResponse getGlobalStats() {
		return globalStatsService.getGlobalStats();
	}
	
}