package kr.co.daou.knock.common.db.mybatis.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Chat {
	private long roomIdx;
	private long userIdx;
	private String contents;
	private Date date;
}
