package comp3011assignment1.config;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class OpenAiConfig {
	
	@Bean
	@Lazy
	public OpenAIClient openAIClient() {
		return OpenAIOkHttpClient.fromEnv();
	}
}
