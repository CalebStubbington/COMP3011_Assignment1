package comp3011assignment1.service;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.models.audio.AudioModel;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import com.openai.models.audio.transcriptions.TranscriptionCreateResponse;

import comp3011assignment1.dto.TranscriptionResponse;

@Service
public class TranscriptionService {
	
	private final OpenAIClient client;
	
	public TranscriptionService (OpenAIClient client) {
		this.client = client;
	}
	
	public TranscriptionResponse transcribe(byte[] audioData) {
		
		TranscriptionCreateParams params =
				TranscriptionCreateParams.builder()
				.file(new ByteArrayInputStream(audioData))
				.model(AudioModel.GPT_4O_MINI_TRANSCRIBE)
				.build();
		
		TranscriptionCreateResponse transcription =
				client.audio()
				.transcriptions()
				.create(params);
		
		TranscriptionResponse response = new TranscriptionResponse (transcription.toString());
		
		return response;
		
		
	}


}
