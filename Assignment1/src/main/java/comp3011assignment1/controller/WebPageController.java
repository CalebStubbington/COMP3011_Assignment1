package comp3011assignment1.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class WebPageController {
	
	@GetMapping("/welcome")
    public Map<String, String> getWelcome() {
        return Map.of("welcome", "Backend is working!");
    }
}