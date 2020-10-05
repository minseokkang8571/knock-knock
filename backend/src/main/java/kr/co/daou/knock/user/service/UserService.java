package kr.co.daou.knock.user.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.daou.knock.common.db.mybatis.dto.LoginRequest;
import kr.co.daou.knock.common.db.mybatis.dto.SignUpRequest;
import kr.co.daou.knock.common.db.mybatis.dto.UserDto;

public interface UserService {
	int registerUser(SignUpRequest signUpRequest);
	int chkEmail(String email);
	long login(LoginRequest loginRequest);
	UserDto getUserInfo(long userIdx);
	Map<String, Object> getInfoByToken(HttpServletRequest request);
}
