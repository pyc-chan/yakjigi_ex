package com.ict.edu.domain.userrxtbl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.domain.userrxtbl.vo.UserRxTblVO;
import com.ict.edu.domain.userrxtbl.mapper.UserRxTblMapper;

@Service
public class UserRxTblServiceImpl implements UserRxTblService {
   @Autowired
   private UserRxTblMapper UserRxTblMapper;

   @Override
   public List<UserRxTblVO> getUserRxTblList() {
      return UserRxTblMapper.getUserRxTblList();
   }

   @Override
   public List<UserRxTblVO> getUserRxTblById(String post_num) {
      // post_num 값 확인
      System.out.println("Received post_num2: " + post_num);
      return UserRxTblMapper.getUserRxTblById(post_num);
   }

   @Override
   public int getUserRxTblUpdate(UserRxTblVO urvo) {
      return UserRxTblMapper.getUserRxTblUpdate(urvo);
   }

   @Override
   public int getUserRxTblDelete(String post_num) {
      return UserRxTblMapper.getUserRxTblDelete(post_num);
   }

   @Override
   public int getUserRxTblWrite(UserRxTblVO urvo) {
      // 호출된 메소드와 파라미터 확인하기 위해 로그 추가
      System.out.println("In getUserRxTblWrite with urvo: " + urvo);  // urvo 객체 출력

      // 실제 데이터베이스 작업
      int result = UserRxTblMapper.getUserRxTblWrite(urvo);  // DAO 호출

      // 결과값 확인하기 위해 로그 추가
      System.out.println("Result from getUserRxTblWrite: " + result);

      return result;  // 결과 반환
   }

}
