package com.ict.edu.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.user.service.UserService;

@Service
public class UserInfoService {
    
    @Autowired
    private UserService userService;
    
    public UserVO getUserVO(){
        // SecurityContextHolder에서 인증 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 인증 객체가 존재하고 인증된 상태인지 확인
        if (authentication != null && authentication.isAuthenticated()) {
            // 사용자 이름(ID) 가져오기
            String user_id = authentication.getName();
            
            // 사용자 정보를 반환
            UserVO uvo = userService.getUserDetail(user_id);
            return uvo;
        }

        // 인증 정보가 없을 경우
        return null;
    }
}
