package com.ict.edu.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.auth.mapper.AuthMapper;
import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private AuthMapper authMapper;
    
    @Override
    public UserVO getUserDetail(String user_id) {
        return authMapper.getUserDetail(user_id);
    }

    @Override
    public int putUserNickName(UserVO uvo) {
        return userMapper.putUserNickName(uvo);
    }

    @Override
    public int putUserProfile(UserVO uvo) {
        return userMapper.putUserProfile(uvo);
    }

    @Override
    public int putUserName(UserVO uvo) {
        return userMapper.putUserName(uvo);
    }

    @Override
    public int putUserGender(UserVO uvo) {
        return userMapper.putUserGender(uvo);
    }

    @Override
    public int putUserPhone(UserVO uvo) {
        return userMapper.putUserPhone(uvo);
    }

    @Override
    public int putUserPassWord(UserVO uvo) {
        return userMapper.putUserPassWord(uvo);
    }

    @Override
    public int postUserJoin(UserVO uvo) {
        return userMapper.postUserJoin(uvo);
    }

    @Override
    public String getUserPassWord(String user_id) {
        return authMapper.getUserPassWord(user_id);
    }
    
}
