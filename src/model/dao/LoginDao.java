package model.dao;

import model.dto.AccountDto;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 사용자 인증 및 사용자 정보 조회
public class LoginDao {
    private static final LoginDao loginDao = new LoginDao();

    private LoginDao() {
    }

    public static LoginDao getInstance() {
        return loginDao;
    }

    public boolean login(AccountDto account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 데이터베이스 연결을 가져옴
            conn = DbUtil.getConnection();

            // 사용자 ID와 비밀번호가 일치하는 레코드를 조회하는 SQL 쿼리
            String sql = "SELECT id, balance FROM store WHERE login_id = ? AND login_pwd = ?";
            ps = conn.prepareStatement(sql);
            // 첫 번째 자리표시자에 사용자 ID를 설정
            ps.setString(1, account.getLoginId());
            // 두 번째 자리표시자에 사용자 비밀번호를 설정
            ps.setString(2, account.getLoginPwd());
            // SQL 쿼리를 실행하여 결과를 ResultSet 객체에 저장
            rs = ps.executeQuery();

            // ResultSet에서 결과를 가져옴
            if (rs.next()) {
                // 'id' 컬럼 값을 가져와 storeId 변수에 저장
                int storeId = rs.getInt("id");
                // 'balance' 컬럼 값을 가져와 balance 변수에 저장
                int balance = rs.getInt("balance");
                // AccountDto 객체에 storeId와 balance 값을 설정
                account.setId(storeId);
                account.setBalance(balance);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("로그인 중 오류 발생: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}
