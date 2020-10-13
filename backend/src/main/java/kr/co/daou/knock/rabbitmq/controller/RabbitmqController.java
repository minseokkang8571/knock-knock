package kr.co.daou.knock.rabbitmq.controller;

import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.mapper.ReviewMapper;
import kr.co.daou.knock.rabbitmq.service.ProducerService;
import kr.co.daou.knock.review.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import kr.co.daou.knock.common.db.mybatis.dto.Code;
import kr.co.daou.knock.common.db.mybatis.dto.Ot;


@Controller
public class RabbitmqController {

    @Autowired
    ProducerService producerService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    
    @MessageMapping("/receive/{roomIdx}")
    public void stompChat(@RequestBody Chat chat, @PathVariable("roomIdx") String roomIdx) throws Exception {
        reviewService.sendChat(chat);
    	producerService.sendChat(chat);
    }
    @MessageMapping("/operation/{roomIdx}")
    public void stompCode(@RequestBody Review review, @PathVariable("roomIdx") String roomIdx) throws Exception {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        Code code = reviewMapper.getCodeForModify(review);
        String tmp = code.getReviewContents();
        Ot ot = new Ot();
        ot = (Ot) vop.get("code:" + code.getIdx());
        if(ot == null) {
        	ot = new Ot();
        	ot.setIdx(Integer.MAX_VALUE);
        }
        //코드 수정
        if(ot.getIdx() < review.getOtIdx()) {
        	review.setOtIdx(review.getOtIdx() + ot.getString().length());
        }
        String result = "";
        if(review.getOtIdx() >= tmp.length()) {
        	result = tmp + review.getOtString();
        } else {
        	String before = tmp.substring(0, review.getOtIdx());
        	String after = tmp.substring(review.getOtIdx());
        	result = before + review.getOtString() + after;        	        	
        }
         
        reviewService.modifyCode(result, review.getCodeIdx());
        ot.setString(review.getOtString());
        System.out.println(ot);
        vop.set("code:" + code.getIdx(), ot);
        producerService.sendCode(review);
    }
}
