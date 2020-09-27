package kr.co.daou.knock.common.db.mybatis.mapper;

import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;

public interface ReviewMapper {
	int createRoom(Room room);
	int createReview(Review review);
}
