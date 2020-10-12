package kr.co.daou.knock.user.service;


import javax.servlet.http.HttpServletRequest;

import kr.co.daou.knock.common.db.mybatis.dto.LoginRequest;
import kr.co.daou.knock.common.db.mybatis.dto.SignUpRequest;

public interface UserService {
	String registerUser(SignUpRequest signUpRequest);
	String login(LoginRequest loginRequest);
	String logout(HttpServletRequest request);
	String getInfoByToken(HttpServletRequest request);
}
