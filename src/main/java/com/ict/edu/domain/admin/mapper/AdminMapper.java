package com.ict.edu.domain.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.auth.vo.AdminVO;
import com.ict.edu.domain.auth.vo.UserVO;

@Mapper
public interface AdminMapper {
    
    // 관리자 리스트
    List<AdminVO> getAdminList();
    
    // 관리자 디테일
    AdminVO getAdminDetail(String admin_idx);
    
    // 관리자 생성
    int postAdminJoin(AdminVO avo);
    
    // 관리자 변경
    int putAdminUpdate(AdminVO avo);
    
    // 관리자 삭제
    int deleteAdmin(AdminVO avo);
    
    // 유저 리스트
    List<UserVO> getUserList();
    
    // 승인 대기중인 전문가 유저 목록
    List<UserVO> getProPenUser(String user_level_idx);
    
    // 관리자 로그인
    AdminVO getAdminLogin(String admin_id);
}
