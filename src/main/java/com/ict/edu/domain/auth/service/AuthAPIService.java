package com.ict.edu.domain.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AuthAPIService {
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailService userDetailService;
    
    // JWT를 생성한다.
    public String generateToken(Map<String, String> request) {
        String user_id = request.get("user_id");
        // role에 user를 넣는다
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", request.get("role"));
        claims.put("user_idx", request.get("user_idx"));
        claims.put("user_id", user_id);
        return jwtUtil.generateToken(user_id,claims);
    }
    
    // 토큰 인증
    public DataVO validateToken(Map<String, String> request) {
        String token = request.get("token");
        log.info("token : {}", token);
        DataVO dvo = new DataVO();
        String user_id = jwtUtil.extractuserName(token);
        UserVO uvo = userDetailService.getUserDetail(user_id);
        if(uvo != null){
            UserDetails userDetails = userDetailService.loadUserByUsername(user_id);
            if(jwtUtil.validateToken(token, userDetails)){
                // 매개변수가 Function이기 때문에 token에서 role값을 가져온다.
                String role = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));
                Map<String, String> newMap = new HashMap<>();
                newMap.put("role", role);
                newMap.put("user_id", user_id);
                token = generateToken(newMap);
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
