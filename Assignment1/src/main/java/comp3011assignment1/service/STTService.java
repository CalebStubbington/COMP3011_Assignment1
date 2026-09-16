package comp3011assignment1.service;

import comp3011assignment1.dto.TranscriptionResponse;

/**
 * Interface service to allow for testing of a mock transcription service and the real-time use
 * of OpenAi in the titan testing.
 */

public interface STTService {
	
	TranscriptionResponse transcribe(byte[] audioData);

}
