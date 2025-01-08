package com.ict.edu.domain.userdose.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.userdose.mapper.UserdoseMapper;
import com.ict.edu.domain.userdose.vo.UserdoseVO;


@Service
public class UserdoseServiceImpl implements UserdoseService {

    @Autowired
    private UserdoseMapper mybasicboardlogMapper;

    @Override
    public List<UserdoseVO> getUserDoses(String userId) {
        return mybasicboardlogMapper.getUserDoses(userId);
    }

    @Override
    public List<UserdoseVO> getDetailsByDateAndUser(String date, String userId) {
        return mybasicboardlogMapper.getDetailsByDateAndUser(date, userId);
    }

    @Override
    public void deleteDose(String userId, String date) {
        mybasicboardlogMapper.deleteDose(userId, date);
    }

}
