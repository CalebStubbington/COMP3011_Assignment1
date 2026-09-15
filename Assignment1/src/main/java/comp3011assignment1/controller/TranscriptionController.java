package comp3011assignment1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import comp3011assignment1.service.TranscriptionService;
import comp3011assignment1.dto.TranscriptionResponse;

@RestController
@RequestMapping("/api/v1")
public class TranscriptionController {
	
	private final TranscriptionService transcriptionService;
	
	public TranscriptionController(TranscriptionService transcriptionService) {
		this.transcriptionService = transcriptionService;
	}
	
	@PostMapping(value = "/transcription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public TranscriptionResponse transcribe(@RequestParam("file") MultipartFile file) {
		
		try {
			byte[] audioData = file.getBytes();
			
			return transcriptionService.transcribe(audioData);
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to transcribe audio", e);
		}
	}

}
