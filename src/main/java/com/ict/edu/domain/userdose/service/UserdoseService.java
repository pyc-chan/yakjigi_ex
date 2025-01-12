package com.ict.edu.domain.userdose.service;

import java.util.List;

import com.ict.edu.domain.userdose.vo.PayloadVO;
import com.ict.edu.domain.userdose.vo.UserdoseVO;


public interface UserdoseService {
    List<UserdoseVO> getUserDoses(String userId);
    List<UserdoseVO> getDetailsByDateAndUser(String date, String userId); // 메서드 정의 추가
    void deleteDose(String userId, String date);
    void saveMyBasicBoardLog(PayloadVO payloadVO);
    void updateDose(PayloadVO payloadVO);
}
