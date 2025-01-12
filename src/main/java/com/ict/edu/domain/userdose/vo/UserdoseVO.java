package com.ict.edu.domain.userdose.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserdoseVO {
    private int dose_idx;
    private int user_idx;
    private int post_num;
    private LocalDate dose_date;
    private String medi_name;
    private String dose_way;
    private String dose_purpose;
    private String dose_other;
}
