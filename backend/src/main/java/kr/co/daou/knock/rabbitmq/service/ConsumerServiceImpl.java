package kr.co.daou.knock.rabbitmq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.daou.knock.chat.service.ChatService;
import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.service.CommonService;
import kr.co.daou.knock.review.service.ReviewService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl extends CommonService implements ConsumerService {

    private static final String QUEUE_NAME_CHAT = "KnockChat";
    private static final String QUEUE_NAME_CODE = "KnockCode";

    private static final String EXCHANGE_NAME = "spring-boot-exchange";
    private static final String ROUTING_KEY = "chat";
    private final SimpMessageSendingOperations messagingTemplate;
    @Autowired
    ChatService chatService;
    @Autowired
    ReviewService reviewService;

    public ConsumerServiceImpl(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @RabbitListener(queues = QUEUE_NAME_CHAT)
    public void receiveMessage(final Message message) {
        log.info("Send Chat starting");
        log.info(message.toString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Chat chat = mapper.readValue(new String(message.getBody()), Chat.class);
            chatService.writeChat(chat);
            messagingTemplate.convertAndSend("/send/"+chat.getRoomIdx(),chat);
            log.info("message.getBody() : "+new String(message.getBody()));
            log.info("Chat : "+chat);
            log.info("Send Chat ending");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = QUEUE_NAME_CODE)
    public void receiveCode(final Message message) {
        log.info("Send Code starting");
        log.info(message.toString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Review review = mapper.readValue(new String(message.getBody()), Review.class);
//          수정이 완료된 시점에서 큐에 등록 후 읽어옴
//            읽어온 데이터 화면에 전달
//            화면에서 데이터 뿌리고 사용자들이 수정할 수 있는 권한 부여
            reviewService.modifyCode(review);
            messagingTemplate.convertAndSend("/send/"+review.getRoomIdx(),review);
            log.info("message.getBody() : "+new String(message.getBody()));
            log.info("review : "+review);
            log.info("Send Code ending");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
