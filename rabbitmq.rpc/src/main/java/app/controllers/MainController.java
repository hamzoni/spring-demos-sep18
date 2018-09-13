package app.controllers;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange exchange;

	@GetMapping
	public String hello() {
		String rk = "rpc";

		return (String) template.convertSendAndReceive(exchange.getName(), rk, "world");
	}
}
