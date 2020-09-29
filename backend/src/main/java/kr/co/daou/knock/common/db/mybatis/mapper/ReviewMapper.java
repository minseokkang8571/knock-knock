package kr.co.daou.knock.common.db.mybatis.mapper;

import java.util.List;

import kr.co.daou.knock.common.db.mybatis.dto.Code;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;

public interface ReviewMapper {
	int createRoom(Room room);
	int copyCode(long articleIdx);
	List<Code> getCode(long articleIdx);
	List<Code> enterRoom(long roomIdx);
	List<Room> getRoom();
	int modifyCode(Review review);
	int reviewLog(Review review);
	int saveCode(long roomIdx);
}
