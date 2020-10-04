package kr.co.daou.knock.user.service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
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
	public long login(LoginRequest loginRequest) {
		String password = Sha256.encrypt(loginRequest.getPassword());
		loginRequest.setPassword(password);
		return userMapper.login(loginRequest);
	}

	@Override
	public UserDto getUserInfo(long userIdx) {
		return userMapper.getUserInfo(userIdx);
	}

	@Override
	public Map<String, Object> getInfoByToken(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = request.getHeader("Authorization");
		JwtService jwt = new JwtService();
		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			token = token.substring(7, token.length());
			String chkJwt = jwt.checkValid(token);
			if(chkJwt.equals("true") || chkJwt.equals("expired")) {
				Claims data = jwt.getUserIdx(token);
				long userIdx = ((Integer) data.get("userIdx")).longValue();
				UserDto user = userMapper.getUserInfo(userIdx);
				map.put("status", true);
				map.put("user", user);
			} else {
				map.put("status", false);
			}
		}
		return map;
	}
	
}
