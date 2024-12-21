package com.ict.edu.domain.auth.vo;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;


@Data
@ToString(exclude = "user_pw")
public class UserVO implements UserDetails{
    
    // 첨부파일을 받음
    private MultipartFile file = null;
    
    private String user_id = "";
    // foreign key
    private String user_level_idx = "";
    private String user_pw = "";
    private String user_profile = "";
    private LocalDate user_reg_date = LocalDate.of(2000, 1, 1);
    
    // SNS에서 가져오는 데이터는 name에 저장하고 nickname을 설정하게 함
    private String user_name = "";
    private String user_nickname = "";
    
    // OAuth2에서 필요한 정보
    private String provider = null;
    private String user_kakao = null;
    private String user_naver = null;
    private String user_google = null;
    
    private LocalDate user_birth_date = null;
    // 기본값 Etc
    private User_gender user_gender = User_gender.Etc;
    
    private String user_email = "";
    private String user_phone = null;
    
    private String admin_idx = null;
    private LocalDate user_out_date = null;
    private String user_out_reason = null;
    private LocalDateTime user_last_login = null;
    
    
    
    // 사용자의 권한을 저장하는 리스트
    private List<GrantedAuthority> authorities = new ArrayList<>();
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return user_pw;
    }
    
    @Override
    public String getUsername() {
        return user_id;
    }
    
    
}

enum User_gender{
    // 남성
    Male,
    // 여성
    Female,
    // 기터
    Other,
    // 미설정(SNS에서 성별 안들어올 경우 대비)
    Etc
}

enum User_level_name{
    // 일반유저
    General,
    // 전문가(대기)유저
    Pro_Pen,
    // 전문가(승인)유저
    Pro_Apr
}

enum User_license{
    일반,
    약사면허,
    의사면허
}