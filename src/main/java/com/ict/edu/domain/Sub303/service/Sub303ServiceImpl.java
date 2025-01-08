package com.ict.edu.domain.Sub303.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.Sub303.mapper.Sub303Mapper;
import com.ict.edu.domain.Sub303.vo.Sub303VO;



@Service
public class Sub303ServiceImpl implements Sub303Service {

    @Autowired
    private Sub303Mapper sub303Mapper;

    @Override
    public List<String> getCities() {
        return sub303Mapper.getCities();
    }

    @Override
    public List<String> getTowns(String city) {
        return sub303Mapper.getTowns(city);
    }

    @Override
    public List<Sub303VO> getCityCoordinates(String city, String town, String keyword) {
        return sub303Mapper.getCityCoordinates(city, town, keyword);
    }

    @Override
    public List<Sub303VO> getCityCoordinatesByCityOnly(String city) {
        return sub303Mapper.getCityCoordinatesByCityOnly(city);
    }

    @Override
    public List<Sub303VO> getCityCoordinatesByKeywordOnly(String keyword) {
        return sub303Mapper.getCityCoordinatesByKeywordOnly(keyword);
    }
}
