package com.ict.edu.domain.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public int deleteAdmin(String admin_idx) {
        return adminMapper.deleteAdmin(admin_idx);
    }
    
    public String getAdminNickName(String admin_idx){
        return adminMapper.getAdminNickName(admin_idx);
    }
    
    // 유저 목록
    @Override
    public List<UserVO> getUserList() {
        return adminMapper.getUserList();
    }
    
    @Override
    // 승인 대기중인 전문가 유저
    public List<UserVO> getProPenUser(String user_level_idx) {
        return adminMapper.getProPenUser(user_level_idx);
    }
    
    
    // 관리자 로그인
    @Override
    public AdminVO getAdminLogin(String admin_id) {
        return adminMapper.getAdminLogin(admin_id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminVO avo = adminMapper.getAdminLogin(username);
        if(avo == null){
            throw new UsernameNotFoundException("없는 아이디 입니다.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_GeneralApr"));  // 권한 설정
        if(avo.getAdmin_level_idx().equals("1")){
            authorities.add(new SimpleGrantedAuthority("ROLE_Super"));
        }
        
        return new User(avo.getAdmin_id(),avo.getAdmin_pw(),authorities);
    }
    
    

}
