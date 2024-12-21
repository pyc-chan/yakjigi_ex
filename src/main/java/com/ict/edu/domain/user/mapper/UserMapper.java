package com.ict.edu.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.auth.vo.UserVO;

@Mapper
public interface UserMapper {
    // 로그인시 
    public UserVO getUsersById(String user_id);
    
    public UserVO findUserByProvider(UserVO uvo);
    
    public void insertUser(UserVO mvo);
    
    public int getMembersJoin(UserVO mvo);
    
    
}