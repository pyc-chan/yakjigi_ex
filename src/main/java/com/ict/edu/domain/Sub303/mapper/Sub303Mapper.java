package com.ict.edu.domain.Sub303.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.edu.domain.Sub303.vo.Sub303VO;


@Mapper
public interface Sub303Mapper {
    List<String> getCities();
    List<String> getTowns(@Param("city") String city);
    List<Sub303VO> getCityCoordinates(
            @Param("city") String city,
            @Param("town") String town,
            @Param("keyword") String keyword);
    List<Sub303VO> getCityCoordinatesByCityOnly(@Param("city") String city);
    List<Sub303VO> getCityCoordinatesByKeywordOnly(@Param("keyword") String keyword);
}
