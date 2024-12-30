package com.ict.edu.domain.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.edu.domain.auth.vo.UserVO;

@Mapper
public interface AuthMapper {
    UserVO selectMember(@Param("username") String user_id);
} 
