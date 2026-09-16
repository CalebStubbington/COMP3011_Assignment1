package comp3011assignment1.service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import comp3011assignment1.dto.ShutdownResponse;
import comp3011assignment1.dto.ErrorResponse;

/**
 * Service for performing graceful shutdowns
 * 
 * An AtomicBoolean is used to prevent multiple shutdown requests
 * from starting more than one shutdown.
 * 
 * Async is used to ensure the request can return a response before the server starts.
 */

@Service
public class ShutdownService {
	
	private final AtomicBoolean shutdownInProgress = new AtomicBoolean(false);
	
	private final ConfigurableApplicationContext context;
	
	public ShutdownService(ConfigurableApplicationContext context) {
		this.context = context;
	}
	
	public ResponseEntity<?> shutdown() {
		
		//Checks if the server is already shutting down
		//CompareAndSet needed for thread safety
		if (!shutdownInProgress.compareAndSet(false, true))
		{
			ErrorResponse error = new ErrorResponse(
					Instant.now(),
					409,
					"Conflict",
					"Graceful shutdown is already in progress.",
					"/api/v1/admin/shutdown"
				);
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

		}
		
		Thread.startVirtualThread(() -> {
			context.close();
		});
		
		ShutdownResponse response = new ShutdownResponse("Graceful shutdown requested.");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
	
}
