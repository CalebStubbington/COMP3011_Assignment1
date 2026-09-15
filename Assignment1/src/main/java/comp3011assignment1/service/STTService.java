package comp3011assignment1.service;

import comp3011assignment1.dto.TranscriptionResponse;

public interface STTService {
	
	TranscriptionResponse transcribe(byte[] audioData);

}
