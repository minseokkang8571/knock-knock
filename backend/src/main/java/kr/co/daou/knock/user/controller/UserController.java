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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@ResponseBody
	public String registerUser(@Valid @RequestBody SignUpRequest signUpRequest ) {
		return userService.registerUser(signUpRequest);
	}
	
	@ApiOperation(value = "로그인", response = List.class)
	@PostMapping("/login")
	@ResponseBody
	public String loginUser(@Valid @RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest);
	}
	
	@ApiOperation(value = "로그아웃", response = List.class)
	@DeleteMapping("/logout")
	@ResponseBody
	public String logoutUser(HttpServletRequest request) {
		return userService.logout(request);
	}
	
	@ApiOperation(value = "사용자 정보 받기", response = UserDto.class)
	@GetMapping("/info")
	@ResponseBody
	public String getInfo(HttpServletRequest request) {
		return userService.getInfoByToken(request);
	}

	
}
