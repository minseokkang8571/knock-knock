package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

@Data
public class Room {
	private long idx;
	private long user_idx;
	private long article_idx;
	private String content;
}
