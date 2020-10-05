package kr.co.daou.knock.rabbitmq.service;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import kr.co.daou.knock.common.db.mybatis.dto.Review;

public interface ProducerService {
	void sendChat(Chat chat);
	void sendCode(Review review);
}
