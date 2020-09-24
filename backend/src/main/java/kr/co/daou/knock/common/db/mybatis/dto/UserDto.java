package kr.co.daou.knock.common.db.mybatis.dto;

import lombok.Data;

@Data
public class UserDto {
	private long idx;
	private String email;
	private String password;
	private String name;
}
