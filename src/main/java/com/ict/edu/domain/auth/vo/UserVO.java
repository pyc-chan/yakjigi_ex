package com.ict.edu.domain.auth.vo;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;
import lombok.ToString;


@Data
@ToString(exclude = "user_pw")
public class UserVO implements UserDetails{
    
    // 첨부파일을 받음
    private MultipartFile file = null;
    
    private String user_idx = "";
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
    
    // Enum형
    // 기본값 Etc
    private User_gender user_gender = User_gender.ETC;
    // 기본값 general
    private User_level_name user_level_name = User_level_name.GENERAL;
    // 기본값 general
    private User_license user_license = User_license.GENERAL;
    
    private String user_email = "";
    private String user_phone = null;
    
    private String admin_idx = null;
    private LocalDate user_out_date = null;
    private String user_out_reason = null;
    private LocalDateTime user_last_login = null;
    
    
    
    // 사용자의 권한을 저장하는 리스트
    private List<GrantedAuthority> authorities = new ArrayList<>();
    
    
    // userdetaile 상속
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
    
    public enum User_gender{
        // 남성
        MALE("Male"),
        // 여성
        FEMALE("Female"),
        // 기타
        OTHER("Other"),
        // 미설정(SNS에서 성별 안들어올 경우 대비)
        ETC("Etc");
        
        private String gender;
        
        // 생성자
        User_gender(String gender) {
            this.gender = gender;
        }
        
        // getter
        @JsonValue
        public String getGender(){
            return gender;
        }
    }
    
    public enum User_level_name{
        // 일반유저
        GENERAL("General"),
        // 전문가(대기)유저
        PRO_PEN("ProPen"),
        // 전문가(승인)유저
        PRO_APR("ProApr"),
        // 전문가(거부)유저
        PRO_DECL("ProDecl");
        
        private String level_name;
        
        // 생성자
        User_level_name(String level_name){
            this.level_name = level_name;
        }
        
        // getter
        @JsonValue
        public String getLevelName(){
            return level_name;
        }
    }
    public enum User_license{
        // 일반유저
        GENERAL("일반"),
        // 약사 유저
        CHEMIST("약사면허"),
        // 의사 유저
        DOCTOR("의사면허");
        
        private String license;
        User_license(String license){
            this.license = license;
        }
        
        @JsonValue
        public String getLicense(){
            return license;
        }
    }
}


