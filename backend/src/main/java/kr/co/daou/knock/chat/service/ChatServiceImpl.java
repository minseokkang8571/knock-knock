package kr.co.daou.knock.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.daou.knock.common.db.mybatis.dto.Msg;
import kr.co.daou.knock.common.db.mybatis.mapper.ChatMapper;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatMapper chatMapper;
	
	@Override
	public boolean writeChat(Msg msg) {
		if(chatMapper.writeChat(msg) > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Msg> getChat(long roomNumber) {
		return chatMapper.getChat(roomNumber);
	}

}
