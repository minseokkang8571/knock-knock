package kr.co.daou.knock.review.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.review.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@ApiOperation(value = "방 생성")
	@PostMapping("/createRoom")
	public ResponseEntity<Map<String, Object>> createRoom(@RequestBody Map<String, Object> params) {
		HttpStatus status = HttpStatus.ACCEPTED;
		Map<String, Object> map = new HashMap<String, Object>();
		long room_idx = reviewService.createRoom(params);
		if(room_idx != 0) {
			map.put("status", true);
			map.put("room_idx", room_idx);
		} else {
			map.put("status", false);
		}
		return new ResponseEntity<Map<String,Object>>(map, status);
	}
	
//	@ApiOperation(value = "방 입장 (getCode)")
//	@GetMapping("/enterRoom/{room_idx}")
//	public ResponseEntity<Map<String, Object>> enterRoom(@PathVariable("room_idx") long room_idx) {
//		HttpStatus status = HttpStatus.ACCEPTED;
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//	}
}
