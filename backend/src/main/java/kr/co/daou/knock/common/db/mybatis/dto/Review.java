package kr.co.daou.knock.common.db.mybatis.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Review {
	private long idx;
	private long roomIdx;  // 어떤 방에서
	private long codeIdx;  // 어떤 코드를
	private long userIdx;  // 누가 수정했는지
	private String contents; 
	private Date date; 
}
