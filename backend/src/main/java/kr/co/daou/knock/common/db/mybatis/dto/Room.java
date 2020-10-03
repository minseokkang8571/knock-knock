package kr.co.daou.knock.common.db.mybatis.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class Room extends Paging{
	@ApiParam(value = "방 idx", required = true)
	private long idx;
	@ApiParam(value = "유저 idx", required = true)
	private long userIdx;
	@ApiParam(value = "게시글 idx", required = true)
	private long articleIdx;
	@ApiParam(value = "리뷰 받을 내용", required = true)
	private String contents;
	@ApiParam(value = "삭제 여부", required = false)
	private String delYn;
	
	@ApiParam(value = "웹소켓을 위한 유저 name", required = false)
	private String name; // userName
	@ApiParam(value = "웹소켓을 위한 게시글 title", required = false)
	private String title; // articleTitle
}
