package app.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import app.entities.Message;

@Component
public class Receiver {
	
	@Autowired
	private JmsTemplate jms;

	@JmsListener(destination = "ServiceB", containerFactory = "factoryB")
	public void receiveMessage(Message msg) {
		System.out.println("Service B: " + msg.getContent());
		// response to service broker
		jms.convertAndSend("ResponseServiceB", msg);
	}
}
