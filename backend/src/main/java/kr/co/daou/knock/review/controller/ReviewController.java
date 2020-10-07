package kr.co.daou.knock.review.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;
import kr.co.daou.knock.review.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@ApiOperation(value = "방 생성")
	@PostMapping("/createRoom")
	@ResponseBody
	public String createRoom(@RequestBody Room room) {
		return reviewService.createRoom(room);
	}
	
	@ApiOperation(value = "방 리스트")
	@GetMapping("/getRoom")
	@ResponseBody
	public String getRoom(Room room) {
		return reviewService.getRoom(room);
	}
	
	@ApiOperation(value = "방 입장 (getCode)")
	@GetMapping("/enterRoom/{roomIdx}")
	@ResponseBody
	public String enterRoom(@PathVariable("roomIdx") long roomIdx) {
		return reviewService.enterRoom(roomIdx);
	}
	
	@ApiOperation(value = "코드 수정")
	@PutMapping("/modifyCode")
	@ResponseBody
	public String modifyCode(@RequestBody Review review) {
		return reviewService.modifyCode(review);
	}
	
	@ApiOperation(value = "코드 저장 (방 나가기)")
	@PutMapping("/saveCode/{roomIdx}")
	@ResponseBody
	public String saveCode(@PathVariable("roomIdx") long roomIdx) {
		return reviewService.saveCode(roomIdx);
	}
	
//	// articleController로 옮기기
//	@ApiOperation(value = "리뷰받은 코드 불러오기")
//	@GetMapping("/getReview/{article_idx}")
//	public ResponseEntity<Map<String, Object>> getReview(@PathVariable("article_idx") long article_idx) {
//		HttpStatus status = HttpStatus.ACCEPTED;
//		Map<String, Object> map = new HashMap<String, Object>();
//		String code = reviewService.getReview(article_idx);
//		if(code != null) {
//			map.put("status", true);
//			map.put("code", code);
//		} else {
//			map.put("status", false);
//		}
//		
//		return new ResponseEntity<Map<String,Object>>(map, status);
//	}
}
