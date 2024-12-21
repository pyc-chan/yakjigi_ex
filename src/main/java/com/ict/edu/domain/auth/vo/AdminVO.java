package com.ict.edu.domain.auth.vo;

import lombok.Data;

@Data
public class AdminVO {
    private String admin_id = "";
    private String admin_level_idx = "";
    private String admin_pw = "";
    private String admin_profile = "";
    private String admin_name = "";
    private String admin_nickname = "";
    private String admin_email = "";
    private String admin_phone = "";
    private Integer admin_out = null;
    
    private Admin_level_name admin_level_name = Admin_level_name.General_Sus;
    private String admin_level_desc = "";
}

enum Admin_level_name{
    // 슈퍼관리자
    Super,
    // 일반관리자(승인)
    General_Apr,
    // 일반관리자(중지)
    General_Sus
    
}
