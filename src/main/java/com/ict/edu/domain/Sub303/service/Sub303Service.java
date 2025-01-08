package com.ict.edu.domain.Sub303.service;

import java.util.List;

import com.ict.edu.domain.Sub303.vo.Sub303VO;


public interface Sub303Service {
    List<String> getCities();
    List<String> getTowns(String city);
    List<Sub303VO> getCityCoordinates(String city, String town, String keyword);
    List<Sub303VO> getCityCoordinatesByCityOnly(String city);
    List<Sub303VO> getCityCoordinatesByKeywordOnly(String keyword);
}
