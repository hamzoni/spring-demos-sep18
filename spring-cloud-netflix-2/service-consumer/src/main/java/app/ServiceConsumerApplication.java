package app;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClientException;

import app.controllers.ConsumerController;

@SpringBootApplication
public class ServiceConsumerApplication {

	public static void main(String[] args) throws RestClientException, IOException {
		ApplicationContext ctx = SpringApplication.run(ServiceConsumerApplication.class, args);

		ConsumerController cc = ctx.getBean(ConsumerController.class);
		cc.getEmployee();
	}

	@Bean
	public ConsumerController cc() {
		return new ConsumerController();
	}
}
