package comp3011assignment1.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Regression tests for GlobalStatService
 * 
 * These tests verify that taken stats start at zero and 
 * the input and output tokens are accumulated correctly across
 * multiple transcription requests.
 * 
 * These tests protect against regressions where token stats could be reset
 * or incorrectly calculated.
 */

public class GlobalStatsServiceTest {
	
	private GlobalStatsService globalStatsService;
	
	@BeforeEach
	void setUp() {
		globalStatsService = new GlobalStatsService();
	}
	
	/**
	 * Verifies that global stats start at zero when first created.
	 */
	
	@Test
	void usageStartAtZero() {
		assertEquals(0, globalStatsService.getInputTokens());
		assertEquals(0, globalStatsService.getOutputTokens());
	}
	
	/**
	 * Verifies that global stats are correctly updated.
	 */
	
	@Test
	void usageAddedCorrectly() {
		globalStatsService.addUsage(100, 50);
		
		assertEquals(100, globalStatsService.getInputTokens());
		assertEquals(50, globalStatsService.getOutputTokens());
	}
	
	/**
	 * Verifies that global stats are updated correctly after mutliple
	 * transcription requests.
	 */
	
	@Test
	void multipleUsageUpdatesAreAddedTogether() {
		globalStatsService.addUsage(100, 50);
		globalStatsService.addUsage(50, 100);
		
		assertEquals(150, globalStatsService.getInputTokens());
		assertEquals(150, globalStatsService.getOutputTokens());
	}
}
