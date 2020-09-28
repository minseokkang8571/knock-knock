package kr.co.daou.knock.review.service;

import java.util.Map;

import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;


public interface ReviewService {
	Map<String, Object> createRoom(Room room);
	Map<String, Object> getRoom();
	Map<String, Object> enterRoom(long articleIdx);
	Map<String, Object> modifyCode(Review review);
	Map<String, Object> saveCode(long roomIdx);
}
