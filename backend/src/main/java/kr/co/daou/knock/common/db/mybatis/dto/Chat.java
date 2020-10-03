package kr.co.daou.knock.common.db.mybatis.dto;

import java.util.Date;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class Chat {
	@ApiParam(value = "방 idx", required = true)
	private String roomIdx;
	@ApiParam(value = "유저 idx", required = true)
	private String userIdx;
	@ApiParam(value = "채팅 내용", required = true)
	private String contents;
	@ApiParam(value = "유저 이름", required = true)
	private String name;
	@ApiParam(value = "전송 시간", required = false)
	private Date date;
}
