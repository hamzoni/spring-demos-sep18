package app;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {
	@RabbitListener(queues = "#{queue1.name}")
	public String receive1(String in) throws InterruptedException {
		return "A: " + in;
	}

	@RabbitListener(queues = "#{queue2.name}")
	public String receive2(String in) throws InterruptedException {
		return "B: " + in;
	}
}
