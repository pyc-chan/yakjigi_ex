package com.ict.edu.domain.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.auth.service.AuthService;
import com.ict.edu.domain.auth.service.UserDetailService;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;

import io.jsonwebtoken.Claims;
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
    
    @Autowired
    private UserDetailService userDetailService;
    
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
    
    // 토큰 인증
    @PostMapping("/validate-token")
    public DataVO validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        log.info("token : ", token);
        DataVO dvo = new DataVO();
        String user_id = jwtUtil.extractuserName(token);
        UserVO uvo = userDetailService.getUserDetail(user_id);
        if(uvo != null){
            UserDetails userDetails = userDetailService.loadUserByUsername(user_id);
            if(jwtUtil.validateToken(token, userDetails)){
                Claims claims = jwtUtil.extractClaim(token, null);
                dvo.setUserDetails(userDetails);
                dvo.setSuccess(true);
                dvo.setMessage("토큰 인증 성공");
                dvo.setToken(token);
            }else{
                dvo.setSuccess(false);
                dvo.setMessage("토큰 인증 실패");
            }
            return dvo;
        }
        dvo.setSuccess(false);
        dvo.setMessage("유저 인증 실패");
        return dvo;
    }
    
    
}
