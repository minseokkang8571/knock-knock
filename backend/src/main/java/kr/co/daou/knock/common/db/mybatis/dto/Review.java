package kr.co.daou.knock.common.db.mybatis.dto;

import java.util.Date;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class Review {
	@ApiParam(value = "리뷰 idx", required = false)
	private long idx;
	@ApiParam(value = "방 idx (어떤 방에서)", required = true)
	private long roomIdx;
	@ApiParam(value = "코드 idx (어떤 코드를)", required = true)
	private long codeIdx;
	@ApiParam(value = "유저 idx (누가 수정했는지)", required = true)
	private long userIdx;
//	@ApiParam(value = "수정 코드", required = true)
//	private String contents; 
	@ApiParam(value = "수정 시간", required = false)
	private Date date;

	@ApiParam(value = "타입값(lock,unlock)")
	private String type;
	@ApiParam(value = "게시글 idx", required = true)
	private long articleIdx;
	
	private int otIdx;
	private String otString;
}
