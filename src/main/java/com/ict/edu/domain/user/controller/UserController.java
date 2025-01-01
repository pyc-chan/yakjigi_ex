package com.ict.edu.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    
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
    @PutMapping("/profile")
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
    @PutMapping("/name")
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
    @PutMapping("/gender")
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
    @PutMapping("/phone")
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
    @PutMapping("/password")
    DataVO putUserPassWord(UserVO uvo, @RequestBody String user_new_pw){
        DataVO response = new DataVO();
        String password = userService.getUserPassWord(uvo.getUser_id());
        if(BCrypt.checkpw(uvo.getPassword(), password)){
            password = BCrypt.hashpw(user_new_pw, BCrypt.gensalt());
            uvo.setUser_pw(password);
            if(userService.putUserPassWord(uvo)>0){
                response.setMessage("비밀번호 변경 성공");
                response.setSuccess(true);
                response.setData(uvo);
            }else{
                response.setSuccess(false);
                response.setMessage("비밀번호 변경 실패");
            }
        }else{
            response.setSuccess(false);
            response.setMessage("현재 비밀번호가 틀렸습니다.");
        }
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
