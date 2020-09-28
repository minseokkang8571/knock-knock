package kr.co.daou.knock.chat.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import kr.co.daou.knock.common.db.mybatis.mapper.ChatMapper;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatMapper chatMapper;
	
	@Override
	public boolean writeChat(Chat chat) {
		if(chatMapper.writeChat(chat) > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<HashMap<String, Object>> getChat(long roomIdx) {
		return chatMapper.getChat(roomIdx);
	}

}
