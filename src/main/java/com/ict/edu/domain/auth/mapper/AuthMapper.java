package com.ict.edu.domain.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.auth.vo.UserVO;

@Mapper
public interface AuthMapper {
    // 유저 디테일
    public UserVO getUserDetail(String user_id);
    
    // 로그인시 
    public UserVO getUsersById(String user_id);
    
    // sns 확인
    public UserVO findUserByProvider(UserVO uvo);
    
    // sns 로그인 join
    public int insertUserByProvider(UserVO uvo);
    
    // 일반 회원 join
    public int insertUserJoin(UserVO uvo);
    
    // 비밀번호 체크
    public String getUserPassWord(String user_id);
    
    // 아이디 찾기
    public List<UserVO> userFindById(String user_email);
    
} 
