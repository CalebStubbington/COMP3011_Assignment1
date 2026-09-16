package comp3011assignment1.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

import comp3011assignment1.service.GlobalStatsService;

public class GlobalStatsRaceConditionTest {

	@Test
	void GlobalStatsWorkWithConcurrentUpdates() throws Exception {
		
		GlobalStatsService stats = new GlobalStatsService();
		
		int numberOfRequests = 250;
		
		ExecutorService executor = Executors.newFixedThreadPool(numberOfRequests);
		
		List<Future<?>> futures = new ArrayList<>();
		
		for (int i = 0; i < numberOfRequests; i++) {
			futures.add(executor.submit(() -> stats.addUsage(10, 5)
				)
			);
		}
		
		for (Future<?> future : futures) {
			future.get();
		}
		
		executor.shutdown();
		
		assertEquals(2500, stats.getInputTokens());
		assertEquals(1250, stats.getOutputTokens());
		
	}
}
