package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

@Data
public class LoginRequest {
	private String email;
	private String password;
}
