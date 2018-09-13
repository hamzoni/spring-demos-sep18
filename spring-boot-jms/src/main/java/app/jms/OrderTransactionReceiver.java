package app.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import app.documents.OrderTransaction;
import app.repositories.OrderTransactionRepository;

@Component
public class OrderTransactionReceiver {

	@Autowired
	private OrderTransactionRepository transactionRepository;

	private int count = 1;

	@JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
	public void receiveMessage(OrderTransaction transaction) {
		System.out.println("<" + count + "> Received <" + transaction + ">");
		transactionRepository.save(transaction);
		count++;
	}
}
