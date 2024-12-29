package com.ict.edu.domain.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.common.util.JwtUtil;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;

@Service
public class AuthService{
    
    @Autowired
    private UserDetailService userDetailService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public DataVO authenticate(UserVO uvo){
        DataVO dvo = new DataVO();
        try{
            // 
            UserVO uservo = userDetailService.getUserDetail(uvo.getUser_id());
            String jwt = jwtUtil.generateToken(uservo.getUser_id());
            
            dvo.setSuccess(true);
            dvo.setToken(jwt);
            dvo.setUserDetails(uservo);
            return dvo;
        } catch(Exception e){
            dvo.setSuccess(false);
            dvo.setMessage(e.getMessage());
            return dvo;
        }
        
    }
    
}
