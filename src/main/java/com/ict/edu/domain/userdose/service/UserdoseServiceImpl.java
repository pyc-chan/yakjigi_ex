package com.ict.edu.domain.userdose.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ict.edu.domain.userdose.mapper.UserdoseMapper;
import com.ict.edu.domain.userdose.vo.PayloadVO;
import com.ict.edu.domain.userdose.vo.UserdoseVO;


@Service
@Transactional
public class UserdoseServiceImpl implements UserdoseService {

    @Autowired
    private UserdoseMapper userdoseMapper;

    private static int postNumCounter = 0; // 서버에서 관리하는 post_num

    @Override
    public List<UserdoseVO> getUserDoses(String userId) {
        return userdoseMapper.getUserDoses(userId);
    }

    @Override
    public List<UserdoseVO> getDetailsByDateAndUser(String date, String userId) {
        // Step 1: userId와 date로 post_num 조회
        Integer postNum = userdoseMapper.getPostNumByUserAndDate(Map.of(
            "userId", userId,
            "doseDate", date
        ));

        if (postNum == null) {
            // 데이터가 없으면 빈 리스트 반환
            return new ArrayList<>();
        }

        // Step 2: post_num으로 관련 데이터 조회
        return userdoseMapper.getDetailsByPostNum(Map.of(
            "userId", userId,
            "postNum", postNum
        ));
    }

    @Override
    public void deleteDose(String userId, String date) {
        userdoseMapper.deleteDose(Integer.parseInt(userId), date);
    }

    @Override
    public synchronized void saveMyBasicBoardLog(PayloadVO payloadVO) {
        // 서버에서 post_num 계산
        if (postNumCounter == 0) {
            // 초기화: DB에서 최대 post_num 가져오기
            postNumCounter = userdoseMapper.getMaxPostNumForUpdate();
        }
        
        int postNum = ++postNumCounter; // post_num 증가

        System.out.println("Generated post_num for this request: " + postNum);

        // 각 약물에 대해 데이터 삽입
        payloadVO.getMedications().forEach(medication -> {
            UserdoseVO vo = new UserdoseVO();
            vo.setUser_idx(payloadVO.getUser_idx());
            vo.setDose_date(payloadVO.getDose_date());
            vo.setDose_other(payloadVO.getDose_other().replaceAll("(?i)<p>|</p>", ""));
            vo.setMedi_name(medication.getMedi_name());
            vo.setDose_way(medication.getDose_way());
            vo.setDose_purpose(medication.getDose_purpose());
            vo.setPost_num(postNum); // 서버에서 생성한 post_num 사용

            userdoseMapper.insertMyBasicBoardLog(vo);
        });
    }

    @Override
    public void updateDose(PayloadVO payloadVO) {
        if (payloadVO.getDose_date() == null) {
            throw new IllegalArgumentException("dose_date is required and cannot be null.");
        }

        // 디버깅 로그 추가
        System.out.println("Received post_num: " + payloadVO.getPost_num());

        // Step 1: 기존 데이터 삭제 (post_num 기준)
        int postNum = payloadVO.getPost_num();
        userdoseMapper.deleteByPostNum(postNum);

        // Step 2: 새로운 데이터 삽입 (같은 post_num으로 삽입)
        payloadVO.getMedications().forEach(medication -> {
            UserdoseVO vo = new UserdoseVO();
            vo.setUser_idx(payloadVO.getUser_idx());
            vo.setDose_date(payloadVO.getDose_date());
            vo.setDose_other(payloadVO.getDose_other().replaceAll("(?i)<p>|</p>", ""));
            vo.setMedi_name(medication.getMedi_name());
            vo.setDose_way(medication.getDose_way());
            vo.setDose_purpose(medication.getDose_purpose());
            vo.setPost_num(postNum); // 동일한 post_num 설정

            // 디버깅 로그 추가
            System.out.println("Inserting record with post_num: " + postNum);

            // 데이터 삽입
            userdoseMapper.insertMyBasicBoardLog(vo);
        });
    }
}

