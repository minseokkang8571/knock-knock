package kr.co.daou.knock.common.db.mybatis.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	@Email(message = "이메일 형식으로 입력하세요.")
	@NotBlank(message = "이메일을 입력하세요.")
	private String email;
	@NotBlank(message = "비밀번호를 입력하세요.")
	private String password;
}
