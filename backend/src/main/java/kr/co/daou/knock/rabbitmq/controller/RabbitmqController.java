package kr.co.daou.knock.rabbitmq.controller;

import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.rabbitmq.service.ProducerService;
import kr.co.daou.knock.review.service.ReviewService;

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
    @Autowired
    ReviewService reviewService;

    @MessageMapping("/receive/{roomIdx}")
    public void stompChat(@RequestBody Chat chat, @PathVariable("roomIdx") String roomIdx) throws Exception {
        reviewService.sendChat(chat);
    	producerService.sendChat(chat);
    }
    @MessageMapping("/unlock/{roomIdx}")
    public void stompCode(@RequestBody Review review, @PathVariable("roomIdx") String roomIdx) throws Exception {
        System.out.println("test in unlock@@ : "+review);
        producerService.sendCode(review);
    }
}
