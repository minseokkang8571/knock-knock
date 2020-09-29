package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

@Data
public class Room {
	private long idx;
	private long userIdx;
	private long articleIdx;
	private String contents;
	private String delYn;
	
	private String name; // userName
	private String title; // articleTitle
}
