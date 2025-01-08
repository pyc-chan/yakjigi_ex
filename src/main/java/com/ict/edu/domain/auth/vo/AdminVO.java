package com.ict.edu.domain.auth.vo;

import com.fasterxml.jackson.annotation.JsonValue;

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
    private int admin_out = 0;
    
    private Admin_level_name admin_level_name = Admin_level_name.GENERAL_SUS;
    private String admin_level_desc = "";
    
    public enum Admin_level_name{
        // 슈퍼관리자
        SUPER("Super"),
        // 일반관리자(승인)
        GENERAL_APR("GeneralApr"),
        // 일반관리자(중지)
        GENERAL_SUS("ProApr");
        
        private String levelName;
        
        Admin_level_name(String levelName) {
            this.levelName = levelName;
        }
        
        @JsonValue
        public String getLevelName(){
            return levelName;
        }
        
    }
    
    
}

