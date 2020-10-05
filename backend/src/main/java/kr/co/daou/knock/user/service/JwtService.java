package kr.co.daou.knock.user.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtService {
	private static final String ENCRYPT_STRING = "intern"; // 비밀키 설정
	private static final String DATA_KEY = "userIdx";
	
	public String createLoginToken(long userIdx, int expireTime) {
		long curTime = System.currentTimeMillis();
		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setExpiration(new Date(curTime + expireTime))
				.setIssuedAt(new Date(curTime))
				.claim(DATA_KEY, userIdx)
				.signWith(SignatureAlgorithm.HS256, this.generateKey())
				.compact();
	}
	
	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = ENCRYPT_STRING.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
		}
		return key;
	}
	
	public String checkValid(final String jwt) {
		 try {
	            Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
	            return "true";
	        } catch (SignatureException ex) {
	        } catch (MalformedJwtException ex) {
	        } catch (ExpiredJwtException ex) {
	        	return "expired";
	        } catch (UnsupportedJwtException ex) {
	        } catch (IllegalArgumentException ex) {
	        }
	        return "false";
	}
	
	public Claims getUserIdx(String jwt) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(this.generateKey())
					.parseClaimsJws(jwt).getBody();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return claims;
	}
}
