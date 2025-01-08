package com.ict.edu.domain.user.service;

import com.ict.edu.domain.auth.vo.UserVO;

public interface UserService {
    
    // 디테일
    UserVO getUserDetail(String user_id);
    
    // 닉네임 수정
    int putUserNickName(UserVO uvo);
    
    // 프로필 사진 수정
    int putUserProfile(UserVO uvo);
    
    // 이름 수정
    int putUserName(UserVO uvo);
    
    // 성별 수정
    int putUserGender(UserVO uvo);
    
    // 휴대전화 번호 수정
    int putUserPhone(UserVO uvo);
    
    // 비밀번호 수정
    int putUserPassWord(UserVO uvo);
    
    // 회원가입
    int postUserJoin(UserVO uvo);
    
    // 비밀번호 확인
    String getUserPassWord(String user_id);
    
    
    
}
