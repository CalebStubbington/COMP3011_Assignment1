package comp3011assignment1.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import comp3011assignment1.dto.GlobalStatsResponse;

/**
 * Service responsible for maintaining the STT input and output usage.
 * 
 * AtomicLong is used to ensure updates remain correct when multiple updates
 * are processed.
 */

@Service
public class GlobalStatsService {
	
	private final AtomicLong inputTokens = new AtomicLong(0);
	private final AtomicLong outputTokens = new AtomicLong(0);
	
	public synchronized void addUsage(long input, long output) {
		inputTokens.addAndGet(input);
		outputTokens.addAndGet(output);
	}
	
	public long getInputTokens() {
		return inputTokens.get();
	}
	
	public long getOutputTokens() {
		return outputTokens.get();
	}
	
	public GlobalStatsResponse getGlobalStats() {
		return new GlobalStatsResponse (inputTokens.get(), outputTokens.get());		
	}
}
