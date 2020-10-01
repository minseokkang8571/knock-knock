package kr.co.daou.knock.chat.service;

import java.util.HashMap;
import java.util.List;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;

public interface ChatService {
	boolean writeChat(Chat chat);
//	List<HashMap<String, Object>> getChat(long roomIdx);
}
