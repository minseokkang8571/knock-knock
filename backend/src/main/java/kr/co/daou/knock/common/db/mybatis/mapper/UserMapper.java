package kr.co.daou.knock.common.db.mybatis.mapper;




import kr.co.daou.knock.common.db.mybatis.dto.LoginRequest;
import kr.co.daou.knock.common.db.mybatis.dto.SignUpRequest;
import kr.co.daou.knock.common.db.mybatis.dto.UserDto;

public interface UserMapper {
	int registerUser(SignUpRequest signUpRequest);
	int chkEmail(String email);
	long login(LoginRequest loginRequest);
	UserDto getUserInfo(long userIdx);
}
