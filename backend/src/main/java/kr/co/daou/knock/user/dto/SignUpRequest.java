package kr.co.daou.knock.user.dto;

import lombok.Data;

@Data
public class SignUpRequest {
	private String email;
	private String password;
	private String name;
}
