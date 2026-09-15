package comp3011assignment1.service;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.audio.AudioModel;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import com.openai.models.audio.transcriptions.TranscriptionCreateResponse;

import comp3011assignment1.dto.TranscriptionResponse;

@Service
public class TranscriptionService {

	public TranscriptionResponse transcribe(byte[] audioData) {
		
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		
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
