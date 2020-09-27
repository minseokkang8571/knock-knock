package kr.co.daou.knock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.daou.knock.user.interceptor.JwtInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	JwtInterceptor jwtInterceptor;
	
    private final long MAX_AGE_SECS = 3600;
    
    private static final String[] EXCLUDE_PATHS = {
    		"/user/**",
            //TODO :: 테스트용(추후 삭제)
            "/article/**","/review/**"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(MAX_AGE_SECS);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATHS);
    }
    
}