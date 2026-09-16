package comp3011assignment1.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import comp3011assignment1.dto.TranscriptionResponse;
import comp3011assignment1.service.TranscriptionService;

@WebMvcTest(TranscriptionController.class)
public class TranscriptionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private TranscriptionService transcriptionService;
	
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
	
//	@Test
//	void getGlobalStatsReturnsAfterTokensUpdated() throws Exception {
//		when(globalStatsService.getInputTokens()).thenReturn(expectedValueTest2);
//		when(globalStatsService.getOutputTokens()).thenReturn(expectedValueTest2);
//		
//		mockMvc.perform(get("/api/v1/global/stats"))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.inputTokens").value(100))
//			.andExpect(jsonPath("$.outputTokens").value(100));
//	}
}
