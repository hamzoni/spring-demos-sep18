package app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BrokerConfig {
	
	// queue name
	public static final String qn = "rpc.q1";
	
	@Bean
	Queue queue() {
		return new Queue(qn);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange("tut.rpc");
	}

	@Bean
	Binding binding(DirectExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with("rpc");
	}

}
