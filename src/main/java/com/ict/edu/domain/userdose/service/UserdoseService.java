package com.ict.edu.domain.userdose.service;

import java.util.List;

import com.ict.edu.domain.userdose.vo.UserdoseVO;


public interface UserdoseService {
    List<UserdoseVO> getUserDoses(String userId);
    List<UserdoseVO> getDetailsByDateAndUser(String date, String userId);
    void deleteDose(String userId, String date);
}

