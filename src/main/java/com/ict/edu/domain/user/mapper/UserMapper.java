package com.ict.edu.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.auth.vo.UserVO;

@Mapper
public interface UserMapper {
    // 로그인시 
    public UserVO getUsersById(String user_id);
    
    // sns 확인
    public UserVO findUserByProvider(UserVO uvo);
    
    // sns 로그인 join
    public void insertUserByProvider(UserVO mvo);
    
    // 일반 회원 join
    public int getUserJoin(UserVO mvo);
    
    // 유저 디테일
    public UserVO getUserDetail(String user_id);
    
    // 비밀번호 체크
    public String getUserPassWord(String user_id);
    
    // 닉네임 변경
    public int putUserNickName(UserVO uvo);
}