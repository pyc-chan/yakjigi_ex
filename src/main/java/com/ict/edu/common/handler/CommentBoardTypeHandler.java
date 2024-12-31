package com.ict.edu.common.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ict.edu.domain.comment.vo.CommentVO.Comment_board;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentBoardTypeHandler extends BaseTypeHandler<Comment_board> {
    // Enum의 value값을 DB에 저장한다.
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Comment_board parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getBoard());
    }
    
    // DB에서 가져온 value값을 비교해서 같은 value값이면 name을 return한다.
    private Comment_board getCommentBoardByBoardName(String boardName) {
        for (Comment_board commentBoard : Comment_board.values()) {
            if (commentBoard.getBoard().equals(boardName)) {
                return commentBoard;
            }
        }
        return null;
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 열 이름을 통해 값을 가져온다.
    @Override
    public Comment_board getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String boardName = rs.getString(columnName);
        return getCommentBoardByBoardName(boardName);
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 열 index를 이용해 값을 가져온다.
    @Override
    public Comment_board getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String boardName = rs.getString(columnIndex);
        return getCommentBoardByBoardName(boardName);
    }
    
    // DB에서 value값을 추출해서 name을 return 한다.
    // 데이터 베이스에 저장된 프로시저(미리 정의된 SQL문의 집합)를 호출하여 값을 가져온다.
    @Override
    public Comment_board getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String boardName = cs.getString(columnIndex);
        return getCommentBoardByBoardName(boardName);
    }
}
