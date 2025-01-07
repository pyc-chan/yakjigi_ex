package com.ict.edu.domain.fna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.fna.mapper.FnaMapper;
import com.ict.edu.domain.fna.vo.FnaVO;

@Service
public class FnaServiceImpl implements FnaService{
    
    @Autowired
    private FnaMapper fnaMapper;
    
    @Override
    public List<FnaVO> getFnaList() {
        return fnaMapper.getFnaList();
    }

    @Override
    public int postFnaJoin(FnaVO fvo) {
        return fnaMapper.postFnaJoin(fvo);
    }

    @Override
    public int putFnaDelete(FnaVO fvo) {
        return fnaMapper.putFnaDelete(fvo.getFna_idx());
    }

    @Override
    public int putFnaUpdate(FnaVO fvo) {
        return fnaMapper.putFnaUpdate(fvo);
    }
    
}
