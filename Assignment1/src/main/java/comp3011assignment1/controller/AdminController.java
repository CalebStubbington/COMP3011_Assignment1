package comp3011assignment1.controller;

import java.time.Instant;
import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comp3011assignment1.service.UptimeService;
import comp3011assignment1.dto.UptimeResponse;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	private final UptimeService uptimeService;
	
	public AdminController(UptimeService uptimeService) {
		this.uptimeService = uptimeService;
	}
	
	@GetMapping(value = "/uptime", produces = MediaType.APPLICATION_JSON_VALUE)
	public UptimeResponse getServerUpdate() {
		return uptimeService.getUptime();
	}

}
