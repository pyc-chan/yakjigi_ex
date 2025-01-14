package com.ict.edu.domain.userrxtbl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRxTblVO {
   private String rx_idx, post_num, user_idx, rx_date, rx_phar_name, medi_name, drug_idx, phar_idx, rx_other, rx_photo, rx_photo_name;
}
