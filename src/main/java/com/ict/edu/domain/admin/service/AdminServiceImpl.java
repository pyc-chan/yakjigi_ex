package com.ict.edu.domain.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.admin.mapper.AdminMapper;
import com.ict.edu.domain.auth.vo.AdminVO;
import com.ict.edu.domain.auth.vo.UserVO;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminMapper adminMapper;
    
    // 관리자 변경
    @Override
    public int putAdminUpdate(AdminVO avo) {
        return adminMapper.putAdminUpdate(avo);
    }
    
    // 관리자 생성
    @Override
    public int postAdminJoin(AdminVO avo) {
        return adminMapper.postAdminJoin(avo);
    }
    
    // 관리자 리스트
    @Override
    public List<AdminVO> getAdminList() {
        return adminMapper.getAdminList();
    }
    
    // 관리자 디테일
    @Override
    public AdminVO getAdminDetail(String admin_idx) {
        return adminMapper.getAdminDetail(admin_idx);
    }
    
    // 관리자 삭제
    @Override
    public int deleteAdmin(AdminVO avo) {
        return adminMapper.deleteAdmin(avo);
    }
    
    // 유저 목록
    @Override
    public List<UserVO> getUserList() {
        return adminMapper.getUserList();
    }
    
    // 승인 대기중인 전문가 유저
    public List<UserVO> getProPenUser(String user_level_idx) {
        return adminMapper.getProPenUser(user_level_idx);
    }

}
