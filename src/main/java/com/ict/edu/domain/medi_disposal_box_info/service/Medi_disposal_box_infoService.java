package com.ict.edu.domain.medi_disposal_box_info.service;

import java.util.List;

import com.ict.edu.domain.medi_disposal_box_info.vo.Medi_disposal_box_infoVO;


public interface Medi_disposal_box_infoService {
    List<String> getCities();
    List<String> getTowns(String city);
    List<Medi_disposal_box_infoVO> getCityCoordinates(String city, String town, String keyword);
    List<Medi_disposal_box_infoVO> getCityCoordinatesByCityOnly(String city);
    List<Medi_disposal_box_infoVO> getCityCoordinatesByKeywordOnly(String keyword);
}
