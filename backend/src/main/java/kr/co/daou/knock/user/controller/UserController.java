package kr.co.daou.knock.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiOperation;
import kr.co.daou.knock.common.db.mybatis.dto.LoginRequest;
import kr.co.daou.knock.common.db.mybatis.dto.SignUpRequest;
import kr.co.daou.knock.common.db.mybatis.dto.UserDto;
import kr.co.daou.knock.user.service.JwtService;
import kr.co.daou.knock.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "회원가입", response = List.class)
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest ) {
		if(userService.chkEmail(signUpRequest.getEmail()) == 1) {
			return ResponseEntity.ok(false);
		}
		userService.registerUser(signUpRequest);
		return ResponseEntity.ok(true);
	}
	
	@ApiOperation(value = "로그인", response = List.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
		HttpStatus status = HttpStatus.ACCEPTED;
		UserDto userDto = new UserDto();
		Map<String, Object> map = new HashMap<String, Object>();
		long userIdx = userService.login(loginRequest);
		if(userIdx > 0) {
			userDto = userService.getUserInfo(userIdx);
			map.put("status", true);
			map.put("user", userDto);
			// 토큰 생성 후 리턴
			JwtService jwt = new JwtService();
			String token = jwt.createLoginToken(userIdx);
			map.put("token", token);
		} else {
			map.put("status", false);
		}
		return new ResponseEntity<Map<String,Object>>(map, status);
	}
	
	@ApiOperation(value = "사용자 정보 받기", response = UserDto.class)
	@GetMapping("/info")
	public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest request) {
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<Map<String,Object>>(userService.getInfoByToken(request), status);
	}
	
//	@ApiOperation(value = "사용자 정보 받기", response = UserDto.class)
//	@PostMapping("/info")
//	public ResponseEntity<Map<String, Object>> getInfo(@RequestBody Map<String, String> param) {
//		HttpStatus status = HttpStatus.ACCEPTED;
//		Map<String, Object> map = new HashMap<String, Object>();
//		JwtService jwt = new JwtService();
//		if(jwt.checkValid(param.get("token"))) {
//			Object data = jwt.getUserIdx(param.get("token"));
//			System.out.println(data);
//			map.put("status", true);
//			map.put("user", data);
//		} else {
//			map.put("status", false);
//		}
//		return new ResponseEntity<Map<String,Object>>(map, status);
//	}
	

	
}
