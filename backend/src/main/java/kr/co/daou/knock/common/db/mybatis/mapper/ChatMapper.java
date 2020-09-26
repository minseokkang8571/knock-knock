package kr.co.daou.knock.common.db.mybatis.mapper;

import java.util.List;

import kr.co.daou.knock.common.db.mybatis.dto.Msg;

public interface ChatMapper {
	int writeChat(Msg msg);
	List<Msg> getChat(long roomNumber);
}
