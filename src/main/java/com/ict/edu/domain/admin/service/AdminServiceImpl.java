package com.ict.edu.domain.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.admin.mapper.AdminMapper;
import com.ict.edu.domain.auth.vo.AdminVO;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminMapper adminMapper;
    
    // 관리자 변경
    @Override
    public int putAdminUpdate(AdminVO avo) {
        return 0;
    }
    
    // 관리자 생성
    @Override
    public int postAdminJoin(AdminVO avo) {
        return 0;
    }
    
    // 관리자 리스트
    @Override
    public List<AdminVO> getAdminList() {
        return null;
    }
    
    // 관리자 디테일
    @Override
    public AdminVO getAdminDetail(String admin_idx) {
        return null;
    }
    
    // 관리자 삭제
    @Override
    public int deleteAdmin(AdminVO avo) {
        return 0;
    }
}
