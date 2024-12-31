package com.ict.edu.common.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ict.edu.domain.auth.vo.UserVO.User_license;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLicenseTypeHandler extends BaseTypeHandler<User_license> {
    // Enum의 value값을 DB에 저장한다.
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, User_license parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getLicense());
    }
    
    // DB에서 가져온 value값을 비교해서 같은 value값이면 name을 return한다.
    private User_license getUserLicenseByLicense(String license) {
        for (User_license userLicense : User_license.values()) {
            if (userLicense.getLicense().equals(license)) {
                return userLicense;
            }
        }
        return null;
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 열 이름을 통해 값을 가져온다.
    @Override
    public User_license getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String license = rs.getString(columnName);
        return getUserLicenseByLicense(license);
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 열 index를 이용해 값을 가져온다.
    @Override
    public User_license getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String license = rs.getString(columnIndex);
        return getUserLicenseByLicense(license);
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 데이터 베이스에 저장된 프로시저(미리 정의된 SQL문의 집합)를 호출하여 값을 가져온다.
    @Override
    public User_license getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String license = cs.getString(columnIndex);
        return getUserLicenseByLicense(license);
    }
    
}
