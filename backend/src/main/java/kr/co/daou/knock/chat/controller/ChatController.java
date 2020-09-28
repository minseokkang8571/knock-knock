package kr.co.daou.knock.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.chat.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {
	@Autowired
	private ChatService chatService;
	
//	@ApiOperation(value="채팅 리스트", response = List.class)
//	@GetMapping("/getChat/{roomNumber}")
//	public ResponseEntity<List<Msg>> getChat(@PathVariable("roomNumber") long roomNumber) {
//		List<Msg> list = chatService.getChat(roomNumber);
//		HttpStatus status = HttpStatus.ACCEPTED;
//		return new ResponseEntity<List<Msg>>(list, status);
//	}
}
