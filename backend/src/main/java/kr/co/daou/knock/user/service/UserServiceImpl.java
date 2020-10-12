package kr.co.daou.knock.user.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import kr.co.daou.knock.common.db.mybatis.dto.LoginRequest;
import kr.co.daou.knock.common.db.mybatis.dto.SignUpRequest;
import kr.co.daou.knock.common.db.mybatis.dto.UserDto;
import kr.co.daou.knock.common.db.mybatis.mapper.UserMapper;
import kr.co.daou.knock.common.service.CommonService;

@Service
public class UserServiceImpl extends CommonService implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public String registerUser(SignUpRequest signUpRequest) {
		Map<String, Object> rtnMap = returnMap();
		if (userMapper.chkEmail(signUpRequest.getEmail()) == 1) {
			rtnMap.put(RESULT_TEXT, RESULT_FAIL);
		} else {
			try {
				String password = Sha256.encrypt(signUpRequest.getPassword());
				signUpRequest.setPassword(password);
				userMapper.registerUser(signUpRequest);
				rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
			} catch (Exception e) {
				defaultExceptionHandling(rtnMap, RESULT_FAIL);
			}
		}
		return jsonFormatTransfer(rtnMap);
	}

	@Override
	public String login(LoginRequest loginRequest) {
		Map<String, Object> rtnMap = returnMap();
		UserDto userDto = new UserDto();
		try {
			ValueOperations<String, Object> vop = redisTemplate.opsForValue();
			String password = Sha256.encrypt(loginRequest.getPassword());
			loginRequest.setPassword(password);
			long userIdx = userMapper.login(loginRequest);
			if (userIdx > 0) {
				userDto = userMapper.getUserInfo(userIdx);
				rtnMap.put("user", userDto);
				// 토큰 생성 후 리턴
				JwtService jwt = new JwtService();
				String accessToken = jwt.createLoginToken(userIdx, 60000 * 30);
				String refreshToken = jwt.createLoginToken(userIdx, 60000 * 60 * 24 * 7);
				rtnMap.put("accessToken", accessToken);
				rtnMap.put("refreshToken", refreshToken);
				vop.set("token:" + accessToken, userDto, 30, TimeUnit.MINUTES);
				rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
			} else {
				rtnMap.put(RESULT_TEXT, RESULT_FAIL);
			}
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		return jsonFormatTransfer(rtnMap);
	}

	// 로그아웃 시 refreshToken Redis에 저장
	@Override
	public String logout(HttpServletRequest request) {
		Map<String, Object> rtnMap = returnMap();
		String token = request.getHeader("Authorization");
		SetOperations<String, Object> sop = redisTemplate.opsForSet();
		try {
			if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
				token = token.substring(7, token.length());
				sop.add("blacklist", token);
				rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
			}
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		return jsonFormatTransfer(rtnMap);
	}

//	@Override
//	public UserDto getUserInfo(long userIdx) {
//		return userMapper.getUserInfo(userIdx);
//	}

	// AuthController로 옮겨서 검증은 인터셉터에서 하도록
	@Override
	public String getInfoByToken(HttpServletRequest request) {
		Map<String, Object> rtnMap = returnMap();
		String token = request.getHeader("Authorization");
		JwtService jwt = new JwtService();
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		try {
			if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
				token = token.substring(7, token.length());
				String chkJwt = jwt.checkValid(token);
				if (chkJwt.equals("true") || chkJwt.equals("expired")) {
					UserDto user = (UserDto) vop.get("token:" + token);
					rtnMap.put("user", user);
					rtnMap.put(RESULT_TEXT, RESULT_SUCCESS);
				} else {
					rtnMap.put(RESULT_TEXT, RESULT_FAIL);
				}
			}
		} catch (Exception e) {
			defaultExceptionHandling(rtnMap, RESULT_FAIL);
		}
		return jsonFormatTransfer(rtnMap);
	}

}
