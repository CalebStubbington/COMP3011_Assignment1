package comp3011assignment1.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * This test simulates more than 200 clients making requests to the API at
 * the same time. It verifies that the server can process concurrent requests 
 * successfully without crashes or significant blocking between requests.
 * 
 * Mainly developed has evidence for the concurrent requirement of the assignment.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConcurrentHttpRequestsTest {

    @LocalServerPort
    private int port;
    
    int numberOfRequests = 250;

    HttpClient client = HttpClient.newHttpClient();
    
    ExecutorService executor = Executors.newFixedThreadPool(numberOfRequests);
    
    List<Future<Integer>> futures = new ArrayList<>();
	
	long startTime = System.currentTimeMillis();
    
	/**
	 * Sends 250 simultaneous requests to the uptime endpoint and verifies
	 * that every request receives a response.
	 */
	
    @Test
    void serverIsRunning() throws Exception {
    	
    	for (int i = 0; i < numberOfRequests; i++) {
    		
    		futures.add(executor.submit(() -> {
	    		HttpRequest request = HttpRequest.newBuilder()
		                .uri(URI.create(
		                    "http://localhost:" + port + "/api/v1/admin/uptime"
		                ))
		                .GET()
		                .build();
		
		        HttpResponse<String> response =
		        		client.send(
		                    request,
		                    HttpResponse.BodyHandlers.ofString()
		                );
		        	return response.statusCode();
		        
    				})
    			);
    	}
    	
    	int successful = 0;
    	
    	for (Future<Integer> future : futures) {
    		if (future.get() == 200) {
    			successful++;
    		}
    	}
    	
    	long elapsed = System.currentTimeMillis() - startTime;
	      
    	executor.shutdown();

        System.out.println("Requests: " + numberOfRequests);
        System.out.println("Successful: " + successful);
        System.out.println("Elapsed time: " + elapsed + " ms");

        assertEquals(numberOfRequests, successful);
    }
}