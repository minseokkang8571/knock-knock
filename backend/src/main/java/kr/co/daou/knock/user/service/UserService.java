package kr.co.daou.knock.user.service;

import kr.co.daou.knock.user.dto.LoginRequest;
import kr.co.daou.knock.user.dto.SignUpRequest;
import kr.co.daou.knock.user.dto.UserDto;

public interface UserService {
	public int registerUser(SignUpRequest signUpRequest);
	public int chkEmail(String email);
	public int login(LoginRequest loginRequest);
	public UserDto getUserInfo(LoginRequest loginRequest);
}
