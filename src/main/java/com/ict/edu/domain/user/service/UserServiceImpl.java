package com.ict.edu.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.auth.vo.UserVO;
import com.ict.edu.domain.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserVO getUserDetail(String user_id) {
        return userMapper.getUserDetail(user_id);
    }

    @Override
    public int putUserNickName(UserVO uvo) {
        return userMapper.putUserNickName(uvo);
    }

    @Override
    public int putUserProfile(UserVO uvo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putUserProfile'");
    }

    @Override
    public int putUserName(UserVO uvo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putUserName'");
    }

    @Override
    public int putUserGender(UserVO uvo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putUserGender'");
    }

    @Override
    public int putUserPhone(UserVO uvo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putUserPhone'");
    }

    @Override
    public int putUserPassWord(UserVO uvo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putUserPassWord'");
    }

    @Override
    public int postUserJoin(UserVO uvo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'postUserJoin'");
    }

    @Override
    public String getUserPassWord(String user_id) {
        return userMapper.getUserPassWord(user_id);
    }
    
}
