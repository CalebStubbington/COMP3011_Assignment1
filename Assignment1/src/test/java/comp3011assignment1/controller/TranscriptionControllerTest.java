package comp3011assignment1.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import comp3011assignment1.dto.TranscriptionResponse;
import comp3011assignment1.service.TranscriptionService;

/**
 * Regression tests for the transcription REST endpoint.
 * 
 * These tests verify that uploaded audio is accepted and passed to the
 * transcription service, which then returns the expected JSON format.
 * 
 * A stubbed transcription service is used so that the controller tests
 * do not require the real OpenAI cloud API or key.
 */

@WebMvcTest(TranscriptionController.class)
public class TranscriptionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private TranscriptionService transcriptionService;
	
	/**
	 * Verifies that a transcription request with a valid audio upload returns
	 * HTTP 200 and contains the transcription return by the STT service.
	 */
	
	@Test
	void transcriptionReturnsText() throws Exception {

		MockMultipartFile fakeAudio = new MockMultipartFile(
				"file",
				"recording.webm",
				"audio/webm",
				"fake audio data".getBytes()
			);
		
		when(transcriptionService.transcribe(any(byte[].class))).thenReturn(new TranscriptionResponse("Hello, I am Caleb this is a Test!"));
		
		mockMvc.perform(multipart("/api/v1/transcription").file(fakeAudio))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.transcription")
					.value("Hello, I am Caleb this is a Test!"));
	}
	
	/**
	 * Verifies that a transcription request without the required audio file
	 * is rejected.
	 */
	
	@Test
	void transcriptionFailsWithoutFile() throws Exception {

		mockMvc.perform(multipart("/api/v1/transcription"))
		.andExpect(jsonPath("$.status").value(500))
		.andExpect(jsonPath("$.error").value("Internal Server Error"))
		.andExpect(jsonPath("$.message").value("An unexpected server error occurred."))
		.andExpect(jsonPath("$.path").value("/api/v1/transcription"));
	}
	
}
