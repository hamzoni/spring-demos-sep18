package app.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import app.entities.Message;

@Component
public class Listener {
	
	@JmsListener(destination = "ResponseServiceB", containerFactory = "factoryB")
	public void receiveB(Message msg) {
		System.out.println("Response from B: " + msg.getContent());
	}
	
	@JmsListener(destination = "ResponseServiceA", containerFactory = "factoryB")
	public void receiveA(Message msg) {
		System.out.println("Response from A: " + msg.getContent());
	}
	
	@JmsListener(destination = "ResponseServiceA1", containerFactory = "factoryB")
	public void receiveA1(Message msg) {
		System.out.println("Response from A1: " + msg.getContent());
	}
	
}
