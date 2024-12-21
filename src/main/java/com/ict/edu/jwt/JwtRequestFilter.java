package com.ict.edu.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ict.edu.common.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("JwtRequestFilter 호출\n");
        
        // 요청 헤더에서 Authorization 값 확인
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        
        // 토큰이 있으며 Authorization 안에 bearer 로 시작
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            // 토큰추출 ("bearer "를 제하고 그 이후부터)
            jwtToken = requestTokenHeader.substring(7);
            log.info(("JwtRequestFilter 추출 메서드\n"));
        }
        
    }

}
