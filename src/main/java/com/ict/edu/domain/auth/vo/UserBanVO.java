package com.ict.edu.domain.auth.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserBanVO {
    private String user_stop_idx = "";
    private String user_idx = "";
    private LocalDate stop_date = null;
    private int stop_period = 0;
    private LocalDate stop_end_date = null;
    private String stop_reason = "";
    private String admin_idx = "";
    
}
