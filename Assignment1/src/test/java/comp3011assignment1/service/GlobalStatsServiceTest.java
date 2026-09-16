package comp3011assignment1.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalStatsServiceTest {
	
	private GlobalStatsService globalStatsService;
	
	@BeforeEach
	void setUp() {
		globalStatsService = new GlobalStatsService();
	}
	
	@Test
	void usageStartAtZero() {
		assertEquals(0, globalStatsService.getInputTokens());
		assertEquals(0, globalStatsService.getOutputTokens());
	}
	
	@Test
	void usageAddedCorrectly() {
		globalStatsService.addUsage(100, 50);
		
		assertEquals(100, globalStatsService.getInputTokens());
		assertEquals(50, globalStatsService.getOutputTokens());
	}
	
	@Test
	void multipleUsageUpdatesAreAddedTogether() {
		globalStatsService.addUsage(100, 50);
		globalStatsService.addUsage(50, 100);
		
		assertEquals(150, globalStatsService.getInputTokens());
		assertEquals(150, globalStatsService.getOutputTokens());
	}
}
