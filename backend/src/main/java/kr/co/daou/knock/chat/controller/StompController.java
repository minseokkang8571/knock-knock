package kr.co.daou.knock.chat.controller;


import kr.co.daou.knock.common.db.mybatis.dto.Review;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class StompController {


/*	Stomp,socket 통신
	@MessageMapping("/receive/{roomIdx}")
	@SendTo("/send/{roomIdx}")
	public Chat stompChat(@RequestBody Chat chat, @PathVariable("roomIdx") String roomIdx) throws Exception {
		chatService.writeChat(chat);
		return chat;
	}
*/
	@MessageMapping("/lock/{roomIdx}")
	@SendTo("/send/{roomIdx}")
	public Review stompLock(@RequestBody Review review, @PathVariable("roomIdx") String roomIdx) throws Exception {
		System.out.println(review);
		return review;
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
