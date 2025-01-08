package com.ict.edu.domain.userdose.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.edu.domain.userdose.vo.UserdoseVO;


@Mapper
public interface UserdoseMapper {
    List<UserdoseVO> getUserDoses(@Param("userId") String userId);

    List<UserdoseVO> getDetailsByDateAndUser(@Param("date") String date, @Param("userId") String userId);

    @Delete("DELETE FROM user_dose WHERE user_idx = #{userId} AND dose_date = #{date}")
    void deleteDose(@Param("userId") String userId, @Param("date") String date);

    void insertMyBasicBoardLog(UserdoseVO requestData);
}
