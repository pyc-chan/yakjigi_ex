package com.ict.edu.domain.medi_disposal_box_info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.medi_disposal_box_info.mapper.Medi_disposal_box_infoMapper;
import com.ict.edu.domain.medi_disposal_box_info.vo.Medi_disposal_box_infoVO;


@Service
public class Medi_disposal_box_infoImpl implements Medi_disposal_box_infoService {

    @Autowired
    private Medi_disposal_box_infoMapper medi_disposal_box_infoMapper;

    @Override
    public List<String> getCities() {
        return medi_disposal_box_infoMapper.getCities();
    }

    @Override
    public List<String> getTowns(String city) {
        return medi_disposal_box_infoMapper.getTowns(city);
    }

    @Override
    public List<Medi_disposal_box_infoVO> getCityCoordinates(String city, String town, String keyword) {
        return medi_disposal_box_infoMapper.getCityCoordinates(city, town, keyword);
    }

    @Override
    public List<Medi_disposal_box_infoVO> getCityCoordinatesByCityOnly(String city) {
        return medi_disposal_box_infoMapper.getCityCoordinatesByCityOnly(city);
    }

    @Override
    public List<Medi_disposal_box_infoVO> getCityCoordinatesByKeywordOnly(String keyword) {
        return medi_disposal_box_infoMapper.getCityCoordinatesByKeywordOnly(keyword);
    }
}
