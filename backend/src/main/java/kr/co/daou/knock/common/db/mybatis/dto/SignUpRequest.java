package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

@Data
public class SignUpRequest {
	private String email;
	private String password;
	private String name;
}
