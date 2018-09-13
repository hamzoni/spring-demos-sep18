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

	@JmsListener(destination = "ServiceA", containerFactory = "factoryA")
	public void receiveMessage(Message msg) {
		System.out.println("Service A: " + msg.getContent());
		// response to service broker
		jms.convertAndSend("ResponseServiceA", msg);
		jms.convertAndSend("ResponseServiceA1", msg);
	}
	
}
