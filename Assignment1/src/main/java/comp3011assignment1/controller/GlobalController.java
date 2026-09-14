package comp3011assignment1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comp3011assignment1.service.UptimeService;
import comp3011assignment1.dto.UptimeResponse;

@RestController
@RequestMapping("/api/v1/global")
public class GlobalController {
	
	private final UptimeService uptimeService;
	
	public GlobalController(UptimeService uptimeService) {
		this.uptimeService = uptimeService;
	}
	
	@GetMapping(value = "/stats")
	public GlobalStatResponse getGlobalStats() {

	}

}