package kr.co.daou.knock.review.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;
import kr.co.daou.knock.review.service.ReviewService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@ApiOperation(value = "방 생성")
	@PostMapping("/createRoom")
	public ResponseEntity<Map<String, Object>> createRoom(@RequestBody Room room) {
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<Map<String,Object>>(reviewService.createRoom(room), status);
	}
	
	@ApiOperation(value = "방 리스트")
	@GetMapping("/getRoom") // 페이징 파라미터 추가하기
	public ResponseEntity<Map<String, Object>> getRoom() {
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<Map<String,Object>>(reviewService.getRoom(), status);
	}
	
	@ApiOperation(value = "방 입장 (getCode)")
	@GetMapping("/enterRoom/{roomIdx}")
	public ResponseEntity<Map<String, Object>> enterRoom(@PathVariable("roomIdx") long roomIdx) {
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<Map<String,Object>>(reviewService.enterRoom(roomIdx), status);
	}
	
	@ApiOperation(value = "코드 수정")
	@PutMapping("/modifyCode")
	public ResponseEntity<Map<String, Object>> modifyCode(@RequestBody Review review) {
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<Map<String,Object>>(reviewService.modifyCode(review), status);
	}
	
	@ApiOperation(value = "코드 저장 (방 나가기)")
	@PutMapping("/saveCode/{roomIdx}")
	public ResponseEntity<Map<String, Object>> saveCode(@PathVariable("roomIdx") long roomIdx) {
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<Map<String,Object>>(reviewService.saveCode(roomIdx), status);
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
