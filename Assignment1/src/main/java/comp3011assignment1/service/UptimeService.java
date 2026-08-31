package comp3011assignment1.service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import comp3011assignment1.dto.UptimeResponse;

@Service
public class UptimeService {
	
	private final Clock clock;
	private final Instant serverStart;
	
	public UptimeService(Clock clock) {
		this.clock = clock;
		this.serverStart = clock.instant();
	}
	
	public UptimeResponse getUptime() {
		Instant utcNow = clock.instant();
		Duration uptime = Duration.between(serverStart, utcNow);
		
		double uptimeSeconds = uptime.getSeconds() + uptime.getNano() / 1_000_000_000.0;
		
		return new UptimeResponse(serverStart, utcNow, uptimeSeconds);
	}

}
