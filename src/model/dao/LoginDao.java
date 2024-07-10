package model.dao;

import model.dto.AccountDto;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 로그인 관련 데이터베이스 작업을 처리하는 DAO 클래스
public class LoginDao {
    // 싱글톤 인스턴스
    private static final LoginDao loginDao = new LoginDao();

    // private 생성자로 외부에서의 인스턴스 생성을 방지
    private LoginDao() {
    }

    // 싱글톤 인스턴스를 반환하는 메서드
    public static LoginDao getInstance() {
        return loginDao;
    }

    // 사용자 로그인을 처리하는 메서드
    public boolean login(AccountDto account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 데이터베이스 연결
            conn = DbUtil.getConnection();
            // 로그인 정보 확인을 위한 SQL 쿼리
            String sql = "SELECT * FROM store WHERE login_id = ? AND login_pwd = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getLoginId());
            ps.setString(2, account.getLoginPwd());
            // 쿼리 실행
            rs = ps.executeQuery();
            // 결과가 존재하면 로그인 성공
            return rs.next();
        } catch (SQLException e) {
            System.out.println("로그인중 에러: " + e.getMessage());
            return false;
        } finally {
            // 사용한 데이터베이스 자원 해제
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}