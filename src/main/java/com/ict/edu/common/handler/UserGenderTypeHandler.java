package com.ict.edu.common.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ict.edu.domain.auth.vo.UserVO.User_gender;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGenderTypeHandler extends BaseTypeHandler<User_gender> {
    // Enum의 value값을 DB에 저장한다.
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, User_gender parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getGender());
    }
    
    // DB에서 가져온 value값을 비교해서 같은 value값이면 name을 return한다.
    private User_gender getUserGenderByGender(String gender) {
        for (User_gender userGender : User_gender.values()) {
            if (userGender.getGender().equals(gender)) {
                return userGender;
            }
        }
        return null;
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 열 이름을 통해 값을 가져온다.
    @Override
    public User_gender getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String gender = rs.getString(columnName);
        return getUserGenderByGender(gender);
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 열 index를 이용해 값을 가져온다.
    @Override
    public User_gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String gender = rs.getString(columnIndex);
        return getUserGenderByGender(gender);
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 데이터 베이스에 저장된 프로시저(미리 정의된 SQL문의 집합)를 호출하여 값을 가져온다.
    @Override
    public User_gender getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String gender = cs.getString(columnIndex);
        return getUserGenderByGender(gender);
    }
}
