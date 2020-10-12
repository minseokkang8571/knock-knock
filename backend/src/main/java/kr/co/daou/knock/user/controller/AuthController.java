package kr.co.daou.knock.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.common.db.mybatis.dto.UserDto;
import kr.co.daou.knock.common.db.mybatis.mapper.UserMapper;
import kr.co.daou.knock.user.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	JwtService jwtService = new JwtService();
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private UserMapper userMapper;

	@ApiOperation("Access 토큰 갱신")
	@GetMapping("/access")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAccessToken(HttpServletRequest request) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String refreshToken = request.getHeader(HEADER_AUTH);
		HttpStatus status = HttpStatus.OK;
		try {
			ValueOperations<String, Object> vop = redisTemplate.opsForValue();
			refreshToken = refreshToken.substring(7, refreshToken.length());
			Claims data = jwtService.getUserIdx(refreshToken);
			long userIdx = ((Integer) data.get("userIdx")).longValue();
			UserDto user = userMapper.getUserInfo(userIdx);
			String accessToken = jwtService.createLoginToken(userIdx, 60000 * 30);
			rtnMap.put("accessToken", accessToken);
				vop.set("token:" + accessToken, user, 30, TimeUnit.MINUTES);
//				accessToken도 받아서 rename 시키기
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(rtnMap, status);

	}

//	// 만료기간이 7일인데 굳이 갱신을 해줘야할지
//	@ApiOperation("Refresh 토큰 갱신")
//	@GetMapping("/refresh")
//	@ResponseBody
//	public ResponseEntity<Map<String, Object>> getRefreshToken(HttpServletRequest request) {
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		String token = request.getHeader(HEADER_AUTH);
//		HttpStatus status = HttpStatus.OK;
//		try {
//			if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
//				token = token.substring(7, token.length());
//				Claims data = jwtService.getUserIdx(token);
//				long userIdx = ((Integer) data.get("userIdx")).longValue();
//				if (jwtService.checkValid(token).equals("true") || jwtService.checkValid(token).equals("expired")) {
//					token = jwtService.createLoginToken(userIdx, 60000 * 60 * 24 * 7);
//					rtnMap.put("refresh", token);
//				} else {
//					status = HttpStatus.UNAUTHORIZED;
//				}
//			}
//		} catch (Exception e) {
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<Map<String, Object>>(rtnMap, status);
//
//	}
}
