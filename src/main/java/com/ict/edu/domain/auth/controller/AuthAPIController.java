package com.ict.edu.domain.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.auth.vo.DataVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthAPIController {
    @Autowired
    private JwtUtil jwtUtil;
    
    // JWT를 생성한다.
    @GetMapping("/generate-token")
    public String generateToken(@RequestBody Map<String, String> request) {
        
        // username 값을 추출
        String username = request.get("username");
        
        // role에 user를 넣는다
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        
        return jwtUtil.generateToken(username,claims);
    }
    
    // 
    @PostMapping("/validate-token")
    public DataVO validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        log.info("token : ", token);
        DataVO dvo = new DataVO();
        
        
        
        
        return null;
    }
    
    
}
