package app.brokers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import app.config.BrokerConfig;

@Component
public class Broker {

	@RabbitListener(queues = BrokerConfig.qn)
	public String hello() {
		return "hi";
	}

}