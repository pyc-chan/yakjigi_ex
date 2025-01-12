package com.ict.edu.domain.userdose.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.edu.domain.userdose.vo.UserdoseVO;


@Mapper
public interface UserdoseMapper {
    // 특정 userId로 복용 기록 조회
    List<UserdoseVO> getUserDoses(String userId);

    // userId와 date로 post_num 조회
    Integer getPostNumByUserAndDate(Map<String, Object> params);

    // 특정 userId와 date로 복용 기록 삭제
    void deleteDose(@Param("userId") int userId, @Param("date") String date);

    // 복용 기록 저장
    void insertMyBasicBoardLog(UserdoseVO requestData);

    // 최대 post_num 조회 (트랜잭션 내 잠금)
    int getMaxPostNumForUpdate();

    // 특정 post_num으로 복용 기록 조회
    List<UserdoseVO> getDetailsByPostNum(Map<String, Object> params);

    // post_num으로 데이터 삭제
    void deleteByPostNum(@Param("postNum") int postNum); // 추가된 메서드
}
