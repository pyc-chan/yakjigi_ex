package com.ict.edu.common.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ict.edu.domain.auth.vo.UserVO.User_level_name;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLevelNameTypeHandler extends BaseTypeHandler<User_level_name> {
    // Enum의 value값을 DB에 저장한다.
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, User_level_name parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getLevelName());
    }
    
    // DB에서 가져온 value값을 비교해서 같은 value값이면 name을 return한다.
    private User_level_name getUserLevelByLevelName(String levelName) {
        for (User_level_name userLevel : User_level_name.values()) {
            if (userLevel.getLevelName().equals(levelName)) {
                return userLevel;
            }
        }
        return null;
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 열 이름을 통해 값을 가져온다.
    @Override
    public User_level_name getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String levelName = rs.getString(columnName);
        return getUserLevelByLevelName(levelName);
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 열 index를 이용해 값을 가져온다.
    @Override
    public User_level_name getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String levelName = rs.getString(columnIndex);
        return getUserLevelByLevelName(levelName);
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 데이터 베이스에 저장된 프로시저(미리 정의된 SQL문의 집합)를 호출하여 값을 가져온다.
    @Override
    public User_level_name getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String levelName = cs.getString(columnIndex);
        return getUserLevelByLevelName(levelName);
    }
}
