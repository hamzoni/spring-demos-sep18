package app;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	@Bean
	public DirectExchange direct() {
		return new DirectExchange("router");
	}

	// receiver config

	@Bean
	public Queue queue1() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue queue2() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding binding1a(DirectExchange direct, Queue queue1) {
		return BindingBuilder.bind(queue1).to(direct).with("a");
	}

	@Bean
	public Binding binding2a(DirectExchange direct, Queue queue2) {
		return BindingBuilder.bind(queue2).to(direct).with("b");
	}

	@Bean
	public Receiver receiver() {
		return new Receiver();
	}
}
