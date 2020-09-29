package kr.co.daou.knock.common.db.mybatis.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Chat {
	private String roomIdx;
	private String userIdx;
	private String contents;
	private String name;
	private Date date;
}
