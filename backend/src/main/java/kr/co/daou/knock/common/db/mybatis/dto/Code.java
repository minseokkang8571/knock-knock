package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

@Data
public class Code {
	private long idx;
	private long articleIdx;
	private String originContents;
	private String reviewContents;
}
