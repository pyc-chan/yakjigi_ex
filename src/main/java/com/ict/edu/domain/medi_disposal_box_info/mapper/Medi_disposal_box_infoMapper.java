package com.ict.edu.domain.medi_disposal_box_info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.edu.domain.medi_disposal_box_info.vo.Medi_disposal_box_infoVO;

@Mapper
public interface Medi_disposal_box_infoMapper {
    List<String> getCities();
    List<String> getTowns(@Param("city") String city);
    List<Medi_disposal_box_infoVO> getCityCoordinates(
            @Param("city") String city,
            @Param("town") String town,
            @Param("keyword") String keyword);
    List<Medi_disposal_box_infoVO> getCityCoordinatesByCityOnly(@Param("city") String city);
    List<Medi_disposal_box_infoVO> getCityCoordinatesByKeywordOnly(@Param("keyword") String keyword);
}
