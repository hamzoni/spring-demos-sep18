package app;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	@Bean
	public DirectExchange direct() {
		return new DirectExchange("router");
	}

	@Bean
	public Sender sender() {
		return new Sender();
	}
}
