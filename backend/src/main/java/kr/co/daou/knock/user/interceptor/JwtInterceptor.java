package kr.co.daou.knock.user.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.daou.knock.user.service.JwtService;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter{
	private static final String HEADER_AUTH = "Authorization";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader(HEADER_AUTH);
		JwtService jwtService = new JwtService();
		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			token = token.substring(7, token.length());
			if(jwtService.checkValid(token)) {
				return true;
			}
		}
		return false;
	}
	
}
