package kr.co.daou.knock.review.service;

import java.util.Map;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;


public interface ReviewService {
	String createRoom(Room room);
	String getRoom(Room room);
	String enterRoom(long articleIdx);
	String modifyCode(String text, long codeIdx);
	String saveCode(long roomIdx);
	String sendChat(Chat chat);
	String getCode(long articleIdx);
}
