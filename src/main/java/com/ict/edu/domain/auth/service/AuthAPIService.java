package com.ict.edu.domain.auth.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.admin.service.AdminService;
import com.ict.edu.domain.auth.vo.AdminVO;
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
    
    @Autowired
    private AdminService adminService;
    
    // JWT를 생성한다.
    public String generateToken(Map<String, String> request) {
        String user_id = request.get("user_id");
        // role에 user를 넣는다
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", request.get("role"));
        log.info(String.valueOf(claims.get("role")));
        if(request.get("admin_idx") != null){
            claims.put("admin_idx",request.get("admin_idx"));
            claims.put("user_id",request.get(user_id));
            log.info(String.valueOf(claims.get("admin_idx")));
        }else{
            claims.put("user_idx", request.get("user_idx"));
            claims.put("user_id", user_id);
        }
        return jwtUtil.generateToken(user_id,claims);
    }
    
    // 토큰 인증
    public DataVO validateToken(Map<String, String> request) {
        String token = request.get("token");
        log.info("token : {}", token);
        DataVO dvo = new DataVO();
        String user_id = jwtUtil.extractuserName(token);
        AdminVO avo = adminService.getAdminLogin(user_id);
        if(avo != null){
            UserDetails userDetails = adminService.loadUserByUsername(user_id);
            if(jwtUtil.validateToken(token, userDetails)){
                String role = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));
                List<GrantedAuthority> authorities = Arrays.stream(role.split(","))
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                    .collect(Collectors.toList());
                log.info(authorities.toString());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, authorities);  // 권한 추가
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // SecurityContext에 설정
                
                dvo.setUserDetails(userDetails);
                dvo.setSuccess(true);
                dvo.setMessage("관리자 토큰 인증 성공");
                return dvo;
            }else{
                dvo.setSuccess(false);
                dvo.setMessage("관리자 토큰 인증 실패");
                return dvo;
            }
        }
        UserVO uvo = userDetailService.getUserDetail(user_id);
        if(uvo != null){
            UserDetails userDetails = userDetailService.loadUserByUsername(user_id);
            if(jwtUtil.validateToken(token, userDetails)){
                // 매개변수가 Function이기 때문에 token에서 role값을 가져온다.
                String role = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));
                
                 // role을 GrantedAuthority로 변환
                List<GrantedAuthority> authorities = Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new) // "ROLE_" 접두어 추가 필요
                .collect(Collectors.toList());
                
                // 인증 객체 생성 및 설정
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, authorities);  // 권한 추가
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // SecurityContext에 설정
                
                dvo.setUserDetails(userDetails);
                dvo.setSuccess(true);
                dvo.setMessage("유저 토큰 인증 성공");
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
