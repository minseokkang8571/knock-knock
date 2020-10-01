package kr.co.daou.knock.common.db.mybatis.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class Code {
	@ApiParam(value = "코드 idx", required = true)
	private long idx;
	@ApiParam(value = "게시글 idx", required = true)
	private long articleIdx;
	@ApiParam(value = "초기 코드", required = true)
	private String originContents;
	@ApiParam(value = "수정 코드", required = true)
	private String reviewContents;
}
