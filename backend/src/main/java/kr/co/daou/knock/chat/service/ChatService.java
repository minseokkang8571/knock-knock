package kr.co.daou.knock.chat.service;

import java.util.List;

import kr.co.daou.knock.common.db.mybatis.dto.Msg;

public interface ChatService {
	boolean writeChat(Msg msg);
	List<Msg> getChat(long roomNumber);
}
