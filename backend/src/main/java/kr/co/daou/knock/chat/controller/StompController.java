package kr.co.daou.knock.chat.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.daou.knock.chat.service.ChatService;
import kr.co.daou.knock.common.db.mybatis.dto.Chat;


@Controller
public class StompController {
	
	@Autowired
	ChatService chatService;
	
	@MessageMapping("/receive/{roomIdx}")
	@SendTo("/send/{roomIdx}")
	public Chat stompChat(@RequestBody Chat chat, @PathVariable("roomIdx") String roomIdx) throws Exception {
		chatService.writeChat(chat);
		return chat;
	}
	
	@MessageMapping("/lock/{roomIdx}")
	@SendTo("/send/{roomIdx}")
	public Map<String, String> stompLock(@RequestBody Map<String, String> params, @PathVariable("roomIdx") String roomIdx) throws Exception {
		return params;
	}
	
	@MessageMapping("/out/{roomIdx}")
	@SendTo("/send/{roomIdx}")
	public String stompOut(@PathVariable("roomIdx") String roomIdx) throws Exception {
		return "out";
	}
	
	
//	@MessageMapping("/sendAlarm/{id}")
//	@SendTo("/receiveAlarm/{id}")
//	public HashMap<String, Object> sendAlarm(@RequestBody Alarm alarm) throws Exception {
//		System.out.println("sendAlarm StompController >>>>> "+alarm);
//		int result = alarmService.sendAlarm(alarm);
//		return alarmService.receiveAlarm(alarm);
//	}
}
