package comp3011assignment1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import comp3011assignment1.service.UptimeService;
import comp3011assignment1.service.ShutdownService;
import comp3011assignment1.dto.UptimeResponse;

/**
 * REST controller for admin operations such as graceful shutdown and uptime stats.
 */

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	private final UptimeService uptimeService;
	private final ShutdownService shutdownService;
	
	public AdminController(UptimeService uptimeService, ShutdownService shutdownService) {
		this.uptimeService = uptimeService;
		this.shutdownService = shutdownService;
	}
	
	@GetMapping(value = "/uptime", produces = MediaType.APPLICATION_JSON_VALUE)
	public UptimeResponse getServerUptime() {
		return uptimeService.getUptime();
	}
	
	@PostMapping(value = "/shutdown", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ShutdownServer() {
		return shutdownService.shutdown();
	}

}
