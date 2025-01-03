package com.ict.edu.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.auth.vo.UserVO;

@Mapper
public interface UserMapper {
    
    // 닉네임 변경
    public int putUserNickName(UserVO uvo);
    
    // 프로필 사진 수정
    public int putUserProfile(UserVO uvo);
    
    // 이름 수정
    public int putUserName(UserVO uvo);
    
    // 성별 수정
    public int putUserGender(UserVO uvo);
    
    // 휴대전화 번호 수정
    public int putUserPhone(UserVO uvo);
    
    // 비밀번호 수정
    int putUserPassWord(UserVO uvo);
    
    // 회원가입
    int postUserJoin(UserVO uvo);
    
}