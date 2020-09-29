package kr.co.daou.knock.common.db.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;

public interface ChatMapper {
	int writeChat(Chat chat);
	List<Chat> getChat(long roomIdx);
}
