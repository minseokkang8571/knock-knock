package kr.co.daou.knock.common.db.mybatis.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class SignUpRequest {
	@Email
	@NotBlank
	@ApiParam(value = "이메일 (형식에 맞게)", required = true)
	private String email;
	@NotBlank
	@ApiParam(value = "비밀번호", required = true)
	private String password;
	@NotBlank
	@ApiParam(value = "이름", required = true)
	private String name;
}
