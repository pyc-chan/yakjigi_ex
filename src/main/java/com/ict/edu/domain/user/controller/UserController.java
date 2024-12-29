package com.ict.edu.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // 디테일
    @GetMapping("/detail")
    public UserVO getUserDetail(String user_id) {
        return userService.getUserDetail(user_id);
    }
    
    // 닉네임 수정
    @PutMapping("/nickname")
    public DataVO putUserNickName(UserVO uvo){
        DataVO response = new DataVO();
        // 작업 성공
        if(userService.putUserNickName(uvo) > 0){
            log.info("닉네임 수정 성공");
            response.setSuccess(true);
            response.setMessage("닉네임이 성공적으로 수정되었습니다.");
        }else{
            log.info("닉네임 수정 실패");
            response.setSuccess(false);
            response.setMessage("닉네임 수정에 실패하였습니다.");
        }
        return response;
    }
    
    // 프로필 사진 수정
    DataVO putUserProfile(UserVO uvo){
        DataVO response = new DataVO();
        if(userService.putUserProfile(uvo)>0){
            log.info("프로필 수정 성공");
            response.setSuccess(true);
            response.setMessage("프로필이 성공적으로 수정되었습니다.");
        }else{
            log.info("프로필 수정 실패");
            response.setSuccess(false);
            response.setMessage("프로필 수정에 실패하였습니다.");
        }
        return response;
    }
    
    // 이름 수정
    DataVO putUserName(UserVO uvo){
        DataVO response = new DataVO();
        if(userService.putUserName(uvo)>0){
            log.info("이름 수정 성공");
            response.setSuccess(true);
            response.setMessage("이름이 성공적으로 수정되었습니다.");
        }else{
            log.info("이름 수정 실패");
            response.setSuccess(false);
            response.setMessage("이름 수정에 실패하였습니다.");
        }
        return response;
    }
    
    // 성별 수정
    DataVO putUserGender(UserVO uvo){
        DataVO response = new DataVO();
        if(userService.putUserGender(uvo)>0){
            log.info("성별 수정 성공");
            response.setSuccess(true);
            response.setMessage("성별이 성공적으로 수정되었습니다.");
        }else{
            log.info("성별 수정 실패");
            response.setSuccess(false);
            response.setMessage("성별 수정에 실패하였습니다.");
        }
        return response;
    }
    
    // 휴대전화 번호 수정
    DataVO putUserPhone(UserVO uvo){
        DataVO response = new DataVO();
        if(userService.putUserPhone(uvo)>0){
            log.info("번호 수정 성공");
            response.setSuccess(true);
            response.setMessage("번호가 성공적으로 수정되었습니다.");
        }else{
            log.info("번호 수정 실패");
            response.setSuccess(false);
            response.setMessage("번호 수정에 실패하였습니다.");
        }
        return response;
    }
    
    // 비밀번호 수정 
    DataVO putUserPassWord(UserVO uvo){
        DataVO response = new DataVO();
        
        
        
        return response;
    }
    
    // 회원가입
    DataVO postUserJoin(UserVO uvo){
        DataVO response = new DataVO();
        if(userService.putUserName(uvo)>0){
            log.info("이름 수정 성공");
            response.setSuccess(true);
            response.setMessage("이름이 성공적으로 수정되었습니다.");
        }else{
            log.info("이름 수정 실패");
            response.setSuccess(false);
            response.setMessage("이름 수정에 실패하였습니다.");
        }
        return response;
    }
    
    
    
    
}
