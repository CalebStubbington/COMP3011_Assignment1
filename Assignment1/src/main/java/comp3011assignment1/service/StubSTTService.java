package comp3011assignment1.service;

import comp3011assignment1.dto.TranscriptionResponse;

/**
 * Stub Service for testing a mock STT service
 */

public class StubSTTService implements STTService {
	
	@Override
	public TranscriptionResponse transcribe(byte[] audioData) {
		return new TranscriptionResponse("Hello, I am Caleb!");
	}

}
