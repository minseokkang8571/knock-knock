package kr.co.daou.knock.common.db.mybatis.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class UserDto {
	@ApiParam(value = "유저 idx", required = true)
	private long idx;
	@ApiParam(value = "이메일", required = true)
	private String email;
	@ApiParam(value = "이름", required = true)
	private String name;
}
