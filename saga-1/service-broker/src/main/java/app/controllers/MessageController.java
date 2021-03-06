package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Message;

@RestController
public class MessageController {
	@Autowired
	private JmsTemplate jms;

	@PostMapping("/transaction")
	public void send(@RequestBody Message msg) {
		System.out.println("Sending a transaction.");
		
		jms.convertAndSend("ServiceA", msg);
		jms.convertAndSend("ServiceB", msg);
	}
}