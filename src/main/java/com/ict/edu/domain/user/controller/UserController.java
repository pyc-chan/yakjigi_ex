package com.ict.edu.domain.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.common.util.FileUploadController;
import com.ict.edu.common.util.UserInfoService;
import com.ict.edu.domain.auth.vo.DataVO;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.qna.service.QnaService;
import com.ict.edu.domain.qna.vo.QnaVO;
import com.ict.edu.domain.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private QnaService qnaService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    // 디테일
    @GetMapping("/detail")
    public UserVO getUserDetail(String user_id) {
        return userService.getUserDetail(user_id);
    }
    
    // 닉네임 수정
    @PutMapping("/nickname")
    public DataVO putUserNickName(UserVO uvo){
        DataVO dvo = new DataVO();
        // 작업 성공
        if(userService.putUserNickName(uvo) > 0){
            log.info("닉네임 수정 성공");
            dvo.setSuccess(true);
            dvo.setMessage("닉네임이 성공적으로 수정되었습니다.");
        }else{
            log.info("닉네임 수정 실패");
            dvo.setSuccess(false);
            dvo.setMessage("닉네임 수정에 실패하였습니다.");
        }
        return dvo;
    }
    
    // 프로필 사진 수정
    @PutMapping("/profile")
    DataVO putUserProfile(UserVO uvo){
        DataVO dvo = new DataVO();
        UserVO olduvo = userService.getUserDetail(uvo.getUser_id());
        FileUploadController fileUploadController = new FileUploadController(uvo.getFile(), "profile");
        dvo = fileUploadController.FileUpdate(olduvo.getUser_profile());
        if(dvo.isSuccess()){
            uvo.setUser_profile(dvo.getData().toString());
            if(userService.putUserProfile(uvo)>0){
                log.info("프로필 수정 성공");
                dvo.setSuccess(true);
                dvo.setMessage("프로필이 성공적으로 수정되었습니다.");
            }else{
                log.info("프로필 수정 실패");
                dvo.setSuccess(false);
                dvo.setMessage("프로필 수정에 실패하였습니다.");
            }
        }
        return dvo;
    }
    
    // 이름 수정
    @PutMapping("/name")
    DataVO putUserName(UserVO uvo){
        DataVO dvo = new DataVO();
        if(userService.putUserName(uvo)>0){
            log.info("이름 수정 성공");
            dvo.setSuccess(true);
            dvo.setMessage("이름이 성공적으로 수정되었습니다.");
        }else{
            log.info("이름 수정 실패");
            dvo.setSuccess(false);
            dvo.setMessage("이름 수정에 실패하였습니다.");
        }
        return dvo;
    }
    
    // 성별 수정
    @PutMapping("/gender")
    DataVO putUserGender(UserVO uvo){
        DataVO dvo = new DataVO();
        if(userService.putUserGender(uvo)>0){
            log.info("성별 수정 성공");
            dvo.setSuccess(true);
            dvo.setMessage("성별이 성공적으로 수정되었습니다.");
        }else{
            log.info("성별 수정 실패");
            dvo.setSuccess(false);
            dvo.setMessage("성별 수정에 실패하였습니다.");
        }
        return dvo;
    }
    
    // 휴대전화 번호 수정
    @PutMapping("/phone")
    DataVO putUserPhone(UserVO uvo){
        DataVO dvo = new DataVO();
        if(userService.putUserPhone(uvo)>0){
            log.info("번호 수정 성공");
            dvo.setSuccess(true);
            dvo.setMessage("번호가 성공적으로 수정되었습니다.");
        }else{
            log.info("번호 수정 실패");
            dvo.setSuccess(false);
            dvo.setMessage("번호 수정에 실패하였습니다.");
        }
        return dvo;
    }
    
    // 비밀번호 수정 
    @PutMapping("/password")
    DataVO putUserPassWord(UserVO uvo, @RequestBody String user_new_pw){
        DataVO dvo = new DataVO();
        String password = userService.getUserPassWord(uvo.getUser_id());
        if(BCrypt.checkpw(uvo.getUser_pw(), password)){
            password = BCrypt.hashpw(user_new_pw, BCrypt.gensalt());
            uvo.setUser_pw(password);
            if(userService.putUserPassWord(uvo)>0){
                dvo.setMessage("비밀번호 변경 성공");
                dvo.setSuccess(true);
                dvo.setData(uvo);
            }else{
                dvo.setSuccess(false);
                dvo.setMessage("비밀번호 변경 실패");
            }
        }else{
            dvo.setSuccess(false);
            dvo.setMessage("현재 비밀번호가 틀렸습니다.");
        }
        return dvo;
    }
    
    // 회원가입
    @PostMapping("/join")
    DataVO postUserJoin(UserVO uvo){
        DataVO dvo = new DataVO();
        if(userService.postUserJoin(uvo)>0){
            log.info("이름 수정 성공");
            dvo.setSuccess(true);
            dvo.setMessage("이름이 성공적으로 수정되었습니다.");
        }else{
            log.info("이름 수정 실패");
            dvo.setSuccess(false);
            dvo.setMessage("이름 수정에 실패하였습니다.");
        }
        return dvo;
    }
    
    
    // 문의 작성
    @PostMapping("/qna_join")
    public DataVO postQnaJoin(QnaVO qvo){
        DataVO dvo = new DataVO();
        UserVO uvo = userInfoService.getUserVO();
        qvo.setUser_idx(uvo.getUser_idx());
        if(qnaService.postQnaJoin(qvo)>0){
            dvo.setMessage("작성 성공");
            dvo.setSuccess(true);
        }else{
            dvo.setMessage("작성 실패");
            dvo.setSuccess(false);
            dvo.setData(qvo);
        }
        return dvo;
    }
    
    // 사용자가 작성한 리스트
    @GetMapping("/qna_user_list")
    public DataVO getUserQnaList(){
        DataVO dvo = new DataVO();
        UserVO uvo = userInfoService.getUserVO();
        List<QnaVO> list = qnaService.getUserQnaList(uvo.getUser_idx());
        if(list != null && list.size()>0){
            dvo.setSuccess(true);
            dvo.setMessage("유저 Qna 리스트 불러오기 완료");
            dvo.setData(list);
        }else{
            dvo.setMessage("작성한 Qna가 없습니다.");
            dvo.setSuccess(false);
        }
        return dvo;
    }
    
}
