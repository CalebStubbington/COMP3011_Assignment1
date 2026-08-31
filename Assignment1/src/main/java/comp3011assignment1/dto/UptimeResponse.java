package comp3011assignment1.dto;

import java.time.Instant;

public record UptimeResponse(Instant utcServerStart, Instant utcNow, double serverUptimeSeconds) {
	
}
