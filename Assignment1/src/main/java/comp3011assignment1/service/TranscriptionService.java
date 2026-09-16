package comp3011assignment1.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.MultipartField;
import com.openai.models.audio.AudioModel;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import com.openai.models.audio.transcriptions.TranscriptionCreateResponse;

import comp3011assignment1.dto.TranscriptionResponse;

/**
 * Service for communicating with the external cloud STT API
 * 
 * The API is created when the transcription is request so that the 
 * application can start without requiring the OPENAI key.
 */

@Service
public class TranscriptionService implements STTService {
	
	private final GlobalStatsService globalStatsService;
	
	public TranscriptionService(GlobalStatsService globalStatsService) {
		this.globalStatsService = globalStatsService;
	}
	
	@Override
	public TranscriptionResponse transcribe(byte[] audioData) {
		
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		
		TranscriptionCreateParams params =
				TranscriptionCreateParams.builder()
				.file(MultipartField.<InputStream>builder()
						.value(new ByteArrayInputStream(audioData))
						.filename("recording.webm")
						.build())
				.model(AudioModel.GPT_4O_MINI_TRANSCRIBE)
				.build();
		
		TranscriptionCreateResponse transcription =
				client.audio()
				.transcriptions()
				.create(params);
		
		var result = transcription.asTranscription();
		
		var usage = result.usage();
		
		globalStatsService.addUsage(
				usage.get().asTokens()._inputTokens().asKnown().get(), 
				usage.get().asTokens()._outputTokens().asKnown().get()
			);
		
		return new TranscriptionResponse(result.text());
		
		
	}


}
