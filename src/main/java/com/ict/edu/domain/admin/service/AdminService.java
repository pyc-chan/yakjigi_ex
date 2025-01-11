package com.ict.edu.domain.admin.service;

import java.util.List;

import com.ict.edu.domain.auth.vo.AdminVO;
import com.ict.edu.domain.auth.vo.UserVO;


public interface AdminService {
    // 관리자 리스트
    List<AdminVO> getAdminList();
    
    // 관리자 디테일
    AdminVO getAdminDetail(String admin_idx);
    
    // 관리자 생성
    int postAdminJoin(AdminVO avo);
    
    // 관리자 변경
    int putAdminUpdate(AdminVO avo);
    
    // 관리자 삭제
    int deleteAdmin(String admin_idx);
    
    // 관리자 닉네임 호출
    String getAdminNickName(String admin_idx);
    
    // 유저 리스트
    List<UserVO> getUserList();
    
    // 승인 대기중인 전문가 유저 리스트
    List<UserVO> getProPenUser(String user_level_idx);
    
    // 관리자 로그인
    AdminVO getAdminLogin(String admin_id);
}
