package kr.co.daou.knock.common.db.mybatis.mapper;

import java.util.List;

import kr.co.daou.knock.common.db.mybatis.dto.Code;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;

public interface ReviewMapper {
	int createRoom(Room room);
	void copyCode(long articleIdx);
	List<Code> getCode(long articleIdx);
	List<Code> enterRoom(long roomIdx);
	int countByDto(Room room);
	List<Room> findAllByDto(Room room);
	int modifyCode(Review review);
	int reviewLog(Review review);
	int saveCode(long roomIdx);
	Code getCodeForModify(Review review);
}
