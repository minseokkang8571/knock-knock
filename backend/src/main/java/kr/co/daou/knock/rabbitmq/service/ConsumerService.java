package kr.co.daou.knock.rabbitmq.service;

import org.springframework.amqp.core.Message;

public interface ConsumerService {
	void receiveMessage(final Message message);
	void receiveCode(final Message message);
}
