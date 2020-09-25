package kr.co.daou.knock.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.user.dto.LoginRequest;
import kr.co.daou.knock.user.dto.SignUpRequest;
import kr.co.daou.knock.user.dto.UserDto;
import kr.co.daou.knock.user.service.JwtService;
import kr.co.daou.knock.user.service.Sha256;
import kr.co.daou.knock.user.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "회원가입", response = List.class)
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest ) {
		if(userService.chkEmail(signUpRequest.getEmail()) == 1) {
			return ResponseEntity.ok(false);
		}
		String password = Sha256.encrypt(signUpRequest.getPassword());
		signUpRequest.setPassword(password);
		userService.registerUser(signUpRequest);
		return ResponseEntity.ok(true);
	}
	
	@ApiOperation(value = "로그인", response = List.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginRequest loginRequest) {
		// 회원정보를 가져와 jwt 생성하기  >> jwt 프론트로 전송
		HttpStatus status = HttpStatus.ACCEPTED;
		UserDto userDto = new UserDto();
		String password = Sha256.encrypt(loginRequest.getPassword());
		loginRequest.setPassword(password);
		Map<String, Object> map = new HashMap<String, Object>();
		if(userService.login(loginRequest) == 1) {
			userDto = userService.getUserInfo(loginRequest);
			map.put("status", true);
			map.put("userinfo", userDto);
			// 토큰 생성 후 리턴
			JwtService jwt = new JwtService();
			String token = jwt.createLoginToken(userDto);
			map.put("token", token);
			Object data = jwt.getUser(token);
			map.put("data", data);
		} else {
			map.put("status", false);
		}
		return new ResponseEntity<Map<String,Object>>(map, status);
	}
	
}
