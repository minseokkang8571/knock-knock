package kr.co.daou.knock.rabbitmq.service;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {
    private static final String QUEUE_NAME_CHAT = "KnockChat";
    private static final String QUEUE_NAME_CODE = "KnockCode";

    private static final String EXCHANGE_NAME = "spring-boot-exchange";

    private static final String CHAT_ROUTING_KEY = "chat";
    private static final String CODE_ROUTING_KEY = "code";
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendChat(Chat chat){
        //큐에 삽입
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, CHAT_ROUTING_KEY, chat);
        }catch (Exception e){
        }
    }
    public void sendCode(Review review){
        //큐에 삽입
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, CODE_ROUTING_KEY, review);
        }catch (Exception e){
        }
    }

}
