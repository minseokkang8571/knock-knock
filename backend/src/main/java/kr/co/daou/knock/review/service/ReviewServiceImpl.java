package kr.co.daou.knock.review.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;
import kr.co.daou.knock.common.db.mybatis.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Override
	public long createRoom(Map<String, Object> map) {
		Room room = new Room();
		room.setUser_idx(Long.valueOf((String) map.get("user_idx")));
		room.setArticle_idx(Long.valueOf((String) map.get("article_idx")));
		room.setContent((String) map.get("content"));
		
		Review review = new Review();
		review.setCode((String) map.get("code"));
		
		if(reviewMapper.createRoom(room) == 1) {
			long room_idx = room.getIdx();
			review.setRoom_idx(room_idx);
			reviewMapper.createReview(review);
			return room_idx;
		}
		return 0;
	}

}
