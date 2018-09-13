package app;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {
	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange direct;

	@Scheduled(fixedDelay = 1000, initialDelay = 500)
	public void send() {
		String result = (String) template.convertSendAndReceive(direct.getName(), "a", "aaa");
		
		System.out.println(result);
//		template.convertAndSend(direct.getName(), "b", "bbb");
	}
}
