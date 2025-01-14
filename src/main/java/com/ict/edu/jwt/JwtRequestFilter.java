package com.ict.edu.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.admin.service.AdminService;
import com.ict.edu.domain.auth.service.AuthAPIService;
import com.ict.edu.domain.auth.vo.DataVO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
// 한번의 요청에 대해 한번만 실행
public class JwtRequestFilter extends OncePerRequestFilter{
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private AuthAPIService authAPIService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("JwtRequestFilter 호출\n");
        /* String requestURI = request.getRequestURI(); */
        /* if (requestURI.startsWith("/api/") || 
            requestURI.startsWith("/oauth/") || 
            requestURI.startsWith("/fna/") || 
            requestURI.startsWith("/download/") || 
            requestURI.startsWith("/auth/") || 
            requestURI.startsWith("/qna/")) {
            filterChain.doFilter(request, response);  // 필터를 통과시킴    
            return;
        } */
        log.info("예외 처리 안됨");
        // 요청 헤더에서 Authorization 값 확인
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null; 
        String jwtToken = null;
        String admin_idx = null;
        
        // 토큰이 있으며 Authorization 안에 bearer 로 시작
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            // 토큰추출 ("bearer "를 제하고 그 이후부터)
            jwtToken = requestTokenHeader.substring(7);
            log.info(("JwtRequestFilter 추출 메서드\n"));
            
            try {
                // 이름추출 (id)
                username = jwtUtil.extractuserName(jwtToken);
                
                admin_idx = jwtUtil.extractClaim(jwtToken, claims -> claims.get("admin_idx", String.class));
                log.info("username : " + username + "\n");
                log.info(admin_idx);    

            } catch (Exception e) {
                logger.warn("JWT Token error");
            }
        }else {
            logger.warn("JWT Token empty");
        }
        
        // 사용자이름(아이디)추출, 현재SecurityContext에 인증정보가 설정되었는지 확인 없으면 다음 단계진행
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(admin_idx != null){
                log.info("admin_idx = "+admin_idx);
                UserDetails userDetails = adminService.loadUserByUsername(username);
                log.info("userDetails.username : " + userDetails.getUsername() + "\n");
                log.info("userDetails.password : " + userDetails.getPassword() + "\n");
                Map<String, String> map = new HashMap<>();
                map.put("token", jwtToken);
                DataVO dvo = authAPIService.validateToken(map);
                if (dvo.isSuccess()) {
                    log.info("토큰 인증 성공");
                    // Spring Security 인증객체 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    
                    // Spring Security 인증객체 추가 세부 정보를 설정
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // 인증 성공 후 아래 코드로 인증 객체를 SecurityContext에 설정 
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    log.info("SecurityContext에 인증 객체 설정");
                } else {
                    log.warn("JWT 토큰이 유효하지 않습니다");
                }
            }else{
                
                // 사용자이름을 가지고 현재 DB에 있는지 검사(MyUserDetailService에 있는 메서드 이용)
                // DataVO에서 username, password
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                log.info("userDetails.username : " + userDetails.getUsername() + "\n");
                log.info("userDetails.password : " + userDetails.getPassword() + "\n");

                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    // Spring Security 인증객체 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Spring Security 인증객체 추가 세부 정보를 설정
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 인증 성공 후 아래 코드로 인증 객체를 SecurityContext에 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    log.warn("JWT 토큰이 유효하지 않습니다");
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
