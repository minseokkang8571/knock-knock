package kr.co.daou.knock.user.mapper;



import org.apache.ibatis.annotations.Mapper;

import kr.co.daou.knock.user.dto.LoginRequest;
import kr.co.daou.knock.user.dto.SignUpRequest;
import kr.co.daou.knock.user.dto.UserDto;

@Mapper
public interface UserMapper {
	public int registerUser(SignUpRequest signUpRequest);
	public int chkEmail(String email);
	public int login(LoginRequest loginRequest);
	public UserDto getUserInfo(LoginRequest loginRequest);
}
