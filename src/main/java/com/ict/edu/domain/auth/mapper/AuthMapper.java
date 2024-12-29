package com.ict.edu.domain.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.edu.domain.auth.vo.UserVO;

@Mapper
public interface AuthMapper {
    UserVO selectMember(@Param("user_id") String user_id);
} 
