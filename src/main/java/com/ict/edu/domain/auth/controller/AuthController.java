package com.ict.edu.domain.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.auth.service.AuthAPIService;
import com.ict.edu.domain.auth.service.UserDetailService;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.user.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthAPIService authAPIService;
    
    @Autowired 
    private UserService userService;
    
    @Autowired
    private UserDetailService userDetailService;
    
    // 로그인
    @PostMapping("/login")
    public DataVO userLogin(UserVO uvo){
        DataVO dvo = new DataVO();
        UserVO uvo2 = userDetailService.getUserDetail(uvo.getUser_id());
        // 아이디 존재여부 확인
        if(uvo2 != null){
            // 비밀번호 맞는지 확인
            if(BCrypt.checkpw(uvo.getUser_pw(), uvo2.getUser_pw())){
                Map<String, String> request = new HashMap<>();
                if(uvo2.getUser_level_idx().equals("1")){
                    request.put("role", "레벨 idx에 따라 변동1");
                }else if(uvo2.getUser_level_idx().equals("2")){
                    request.put("role", "레벨 idx에 따라 변동2");
                }else if(uvo2.getUser_level_idx().equals("3")){
                    request.put("role", "레벨 idx에 따라 변동3");
                }
                request.put("user_id", uvo2.getUser_id());
                String token = authAPIService.generateToken(request);
                dvo.setData(token);
                dvo.setSuccess(true);
                dvo.setMessage("토큰 생성 완료");
            }
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("아이디 혹은 비밀번호가 올바르지 않습니다.");
        }
        return dvo;
    }
    
    // 회원가입시 이메일 확인
    
    
    // 회원가입
    
}
