package kr.co.daou.knock.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.daou.knock.common.db.mybatis.dto.LoginRequest;
import kr.co.daou.knock.common.db.mybatis.dto.SignUpRequest;
import kr.co.daou.knock.common.db.mybatis.dto.UserDto;
import kr.co.daou.knock.common.db.mybatis.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;

	@Override
	public int registerUser(SignUpRequest signUpRequest) {
		String password = Sha256.encrypt(signUpRequest.getPassword());
		signUpRequest.setPassword(password);
		return userMapper.registerUser(signUpRequest);
	}

	@Override
	public int chkEmail(String email) {
		return userMapper.chkEmail(email);
	}

	@Override
	public int login(LoginRequest loginRequest) {
		String password = Sha256.encrypt(loginRequest.getPassword());
		loginRequest.setPassword(password);
		return userMapper.login(loginRequest);
	}

	@Override
	public UserDto getUserInfo(LoginRequest loginRequest) {
		return userMapper.getUserInfo(loginRequest);
	}
	
}
