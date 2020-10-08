package kr.co.daou.knock.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.user.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private static final String HEADER_AUTH = "Authorization";
	
	@Autowired
	JwtService jwtService = new JwtService();
	
	@ApiOperation("Access 토큰 갱신")
	@GetMapping("/access")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAccessToken(HttpServletRequest request) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String token = request.getHeader(HEADER_AUTH);
		HttpStatus status = HttpStatus.OK;
		try {
			if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
				token = token.substring(7, token.length());
				Claims data = jwtService.getUserIdx(token);
				long userIdx = ((Integer) data.get("userIdx")).longValue();
				if(jwtService.checkValid(token).equals("true") || jwtService.checkValid(token).equals("expired")) {
					token = jwtService.createLoginToken(userIdx, 60000);
					rtnMap.put("token", token);
				} else {
					status = HttpStatus.UNAUTHORIZED;
				}
			}			
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(rtnMap, status);
		
	}

	
	//만료기간이 7일인데 굳이 갱신을 해줘야할지
	@ApiOperation("Refresh 토큰 갱신")
	@GetMapping("/refresh")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getRefreshToken(HttpServletRequest request) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String token = request.getHeader(HEADER_AUTH);
		HttpStatus status = HttpStatus.OK;
		try {
			if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
				token = token.substring(7, token.length());
				Claims data = jwtService.getUserIdx(token);
				long userIdx = ((Integer) data.get("userIdx")).longValue();
				if(jwtService.checkValid(token).equals("true") || jwtService.checkValid(token).equals("expired")) {
					token = jwtService.createLoginToken(userIdx, 60000 * 60 * 24 * 7);
					rtnMap.put("refresh", token);
				} else {
					status = HttpStatus.UNAUTHORIZED;
				}
			}			
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(rtnMap, status);
		
	}
}
