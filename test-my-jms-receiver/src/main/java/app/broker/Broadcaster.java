package app.broker;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class Broadcaster {

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {

		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");

		return converter;

	}

	@Bean
	public JmsListenerContainerFactory<?> jmsf(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setErrorHandler(new ErrorHandler() {

			public void handleError(Throwable t) {

				try {
					throw t;
				} catch (Throwable e) {
					e.printStackTrace();
				}

			}

		});

		factory.setErrorHandler(t -> System.out.println(t.getMessage()));

		configurer.configure(factory, connectionFactory);

		return factory;
	}

}
