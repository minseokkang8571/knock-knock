package kr.co.daou.knock.rabbitmq.controller;

import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.rabbitmq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;


@Controller
public class RabbitmqController {

    @Autowired
    ProducerService producerService;

    @MessageMapping("/receive/{roomIdx}")
    public void stompChat(@RequestBody String map, @PathVariable("roomIdx") String roomIdx) throws Exception {
        System.out.println(map);
        Chat chat = new Chat();
        String[] temp = map.split(",");
        chat.setRoomIdx(temp[0].split(":")[1].split("\"")[1]);
        chat.setUserIdx(temp[1].split(":")[1].split("\"")[0]);
        chat.setContents(temp[2].split(":")[1].split("\"")[1]);
        chat.setName(temp[3].split(":")[1].split("\"")[1]);

        System.out.println(chat);
        producerService.sendChat(chat);
    }
    @MessageMapping("/unlock/{roomIdx}")
    public void stompCode(@RequestBody Review review, @PathVariable("roomIdx") String roomIdx) throws Exception {
        System.out.println("test : "+review);
        producerService.sendCode(review);
    }


}
