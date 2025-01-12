package com.ict.edu.domain.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.domain.auth.vo.AdminVO;
import com.ict.edu.domain.auth.vo.UserVO;

@Mapper
public interface AdminMapper {
    
    // 관리자 리스트
    public List<AdminVO> getAdminList();
    
    // 관리자 디테일
    public AdminVO getAdminDetail(String admin_idx);
    
    // 관리자 생성
    public int postAdminJoin(AdminVO avo);
    
    // 관리자 변경
    public int putAdminUpdate(AdminVO avo);
    
    // 관리자 삭제
    public int deleteAdmin(String admin_idx);
    
    // 관리자 닉네임 호출
    String getAdminNickName(String admin_idx);
    
    // 유저 리스트
    public List<UserVO> getUserList();
    
    // 승인 대기중인 전문가 유저 목록
    public List<UserVO> getProPenUser(String user_level_idx);
    
    // 관리자 로그인
    public AdminVO getAdminLogin(String admin_id);
    
    // 관리자 아이디 중복 확인
    public int getAdminIDChk(String admin_id);
}
