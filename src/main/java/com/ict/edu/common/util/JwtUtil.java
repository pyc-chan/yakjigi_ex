package com.ict.edu.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    // 보안키
    @Value("${jwt.secret}")
    private String secretKey;
    
    // 만료
    @Value("${jwt.expiration}")
    private long expiration;
    
    // 보안키를 실제 secretKey로 변환
    private SecretKey getKey(){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    // 토큰 생성
    public String generateToken(String user_id) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(user_id, claims);
    }
    // 클레임이란 JWT에 담길 정보로 사용자권한, 역할등 관련정보를 담는다.
    // 다만 보안이 취약하니 민감한 개인정보는 담지 않는다.
    
    // 토큰 생성 오버라이드
    public String generateToken(String user_id, Map<String, Object> claims){
        return Jwts.builder() // JWT 생성하는 빌더 객체
                .setClaims(claims) // JWT에 포함될 클레임
                .setSubject(user_id) // JWT의 주체를 설정하는것으로 고유 식별자를 나타냄
                .setIssuedAt(new Date()) // JWT가 발급된 시간을 설정한다.
                .setExpiration(new Date(System.currentTimeMillis()+expiration)) // JWT의 만료시간을 설정한다.
                .signWith(getKey(), SignatureAlgorithm.HS256) // JWT에 서명을 추가하는데 getKey는 JWT의 무결성을 보장한다.
                // SignatureAlgorithm.HS256 는 HMAC SHA-256 알고리즘을 사용하여 서명할 것을 의미함
                .compact(); // 설정된 정보를 바탕으로 JWT를 생성하고 문자열로 반환한다.
    }
    
    
    // 모든 정보 추출
    // 생성된 토큰에서 다시 정보를 추출하는것
    private Claims extractAllClaims(String token){
        // JWT를 파싱하는 빌더 객체
        return Jwts.parserBuilder() 
                // JWT의 서명을 검증하는 비밀키 설정
                .setSigningKey(getKey()) 
                // 설정된 내용으로 JWT 파서 객체 생성
                .build() 
                // JWT 문자열을 파싱하여 서명을 검증하고 유효한 경우
                // 클레임을 포함하는 JWS(JSON Web Signature) 객체를 반환한다.
                .parseClaimsJws(token) 
                // 파싱된 JWS 객체에서 클레임을 추출하고 반환한다.
                .getBody (); 
    }
    
    // 다양한 타입의 클레임을 추출할 수 있는 메서드
    // claimsResolver : 클레임을 처리하는 로직을 정의한 함수
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token); // 토큰에서 추출한 클레임을 claims에 넣는다.
        return claimsResolver.apply(claims); // 입력한 함수에 claims 객체를 매개변수로 T를 반환한다.
    }
    
    // 토근을 받아서 이름 추출한다.
    public String extractuserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    // 토큰 검사
    // UserDetails는 유저 정보를 로드하며, 관리하는 역할 한다.
    public Boolean validateToken(String token, UserDetails userDetails) {
        // jwt 토큰에서 subject 정보를 가져오는 것
        final String username = extractuserName(token);
        // 토큰의 username과 매개변수 userDetails의 username을 비교해서 같은지 확인
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    // 만료시간 점검
    public Boolean isTokenExpired(String token) {
        // 토큰에서 만료시간을 추출해 현재시간과 비교
        // 만료시간이 현재시간보다 이전이면 true(만료된 토큰)
        return extractExpiration(token).before(new Date());
    }
    
    // 만료시간 추출
    public Date extractExpiration(String token) {
        // Claims의 getExpiration을 이용하여 만료시간 추출
        return extractClaim(token, Claims::getExpiration);
    }
    
}

