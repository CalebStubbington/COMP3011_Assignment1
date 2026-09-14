package comp3011assignment1.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import comp3011assignment1.dto.ShutdownResponse;

@Service
public class ShutdownService implements ApplicationContextAware {
	
	private ApplicationContext context;
	
	public ShutdownResponse shutdown() {
		new Thread(() -> {
	           try {
	               Thread.sleep(100);
	               ((ConfigurableApplicationContext)context).close();
	           } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
		return new ShutdownResponse("Graceful shutdown requested.");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.context = ctx;
	}
}
