//package kr.co.daou.knock.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnection;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//@Configuration
//public class RedisConfig {
//	
//	@Value("${spring.redis.port}")
//	public int port;
//	
//	@Value("${spring.redis.host}")
//	public String host;
//	
//	@Autowired
//	public ObjectMapper objectMapper;
//	
//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		return new LettuceConnectionFactory(host, port);
//	}
//	
//	@Bean
//	public RedisTemplate<?, ?> redisTemplate() {
//		RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		return redisTemplate;
//	}
//}
