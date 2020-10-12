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
import org.springframework.web.bind.annotation.CrossOrigin;
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
		System.out.println("aaa");
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
}
