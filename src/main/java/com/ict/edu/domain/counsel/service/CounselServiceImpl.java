package com.ict.edu.domain.counsel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.counsel.mapper.CounselMapper;
import com.ict.edu.domain.counsel.vo.CounselVO;


@Service
public class CounselServiceImpl implements CounselService{
    
    @Autowired
    private CounselMapper counselMapper;

    @Override
    public List<CounselVO> getCounselList() {
        // 상담 리스트 가져오기
        return counselMapper.getCounselList();
    }

    @Override
    public CounselVO getCounselDetail(String counsel_idx) {
        // 특정 상담 디테일 가져오기
        return counselMapper.getCounselDetail(counsel_idx);
    }

    @Override
    public int postCounselJoin(CounselVO counvo) {
        // 상담 작성
        return counselMapper.postCounselJoin(counvo);
    }

    @Override
    public int putCounselUpdate(CounselVO counvo) {
        // 상담 수정
        return counselMapper.putCounselUpdate(counvo);
    }

    @Override
    public int putCounselDelete(CounselVO counvo) {
        // 상담 삭제 (실제로는 update)
        return counselMapper.putCounselDelete(counvo);
    }

    @Override
    public int putCounselCommentJoin(CounselVO counvo) {
        // 상담 응답 작성
        return counselMapper.putCounselCommentJoin(counvo);
    }
}
