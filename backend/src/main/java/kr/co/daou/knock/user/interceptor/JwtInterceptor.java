package kr.co.daou.knock.user.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.daou.knock.user.service.JwtService;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter{
	private static final String HEADER_AUTH = "Authorization";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader(HEADER_AUTH);
		JwtService jwtService = new JwtService();
		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			token = token.substring(7, token.length());
			SetOperations<String, Object> sop = redisTemplate.opsForSet();
			if(sop.isMember("blacklist", token)) {
				return false;
			}
			if(jwtService.checkValid(token).equals("true")) {
				return true;
			} else if(jwtService.checkValid(token).equals("expired")) {
				response.sendError(444, "expired");
			}
		}
		return false;
	}
	
}
