package app.broker;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import app.entities.Message;

@Component
public class Receiver {

	private int count = 1;

	@JmsListener(destination = "MyMessageQueue", containerFactory = "jmsf")
	public void receiveMessage(Message msg) {
		System.out.println("<" + count + "> Received <" + msg + ">");
		count++;
	}
}
