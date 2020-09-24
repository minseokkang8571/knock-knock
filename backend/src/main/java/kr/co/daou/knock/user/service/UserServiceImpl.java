package kr.co.daou.knock.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.daou.knock.user.dto.LoginRequest;
import kr.co.daou.knock.user.dto.SignUpRequest;
import kr.co.daou.knock.user.dto.UserDto;
import kr.co.daou.knock.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;

	@Override
	public int registerUser(SignUpRequest signUpRequest) {
		return userMapper.registerUser(signUpRequest);
	}

	@Override
	public int chkEmail(String email) {
		return userMapper.chkEmail(email);
	}

	@Override
	public int login(LoginRequest loginRequest) {
		return userMapper.login(loginRequest);
	}

	@Override
	public UserDto getUserInfo(LoginRequest loginRequest) {
		return userMapper.getUserInfo(loginRequest);
	}
	
}
