package model.dao;

import model.dto.AccountDto;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 회원가입 관련 데이터베이스 작업을 처리하는 DAO 클래스
public class SignupDao {
    // 싱글톤 인스턴스
    private static final SignupDao signupDao = new SignupDao();

    // private 생성자로 외부에서의 인스턴스 생성을 방지
    private SignupDao() {
    }

    // 싱글톤 인스턴스를 반환하는 메서드
    public static SignupDao getInstance() {
        return signupDao;
    }

    // 새로운 계정을 데이터베이스에 등록하는 메서드
    public boolean signup(AccountDto account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 데이터베이스 연결
            conn = DbUtil.getConnection();

            // 이미 존재하는 사용자명인지 확인
            String checkSql = "SELECT COUNT(*) FROM store WHERE login_id = ?";
            ps = conn.prepareStatement(checkSql);
            ps.setString(1, account.getLoginId());
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("존재하는 아이디입니다.");
                return false;
            }

            // 새로운 계정 정보 삽입
            String insertSql = "INSERT INTO store (login_id, login_pwd, current_turn) VALUES (?, ?, 1)";
            ps = conn.prepareStatement(insertSql);
            ps.setString(1, account.getLoginId());
            ps.setString(2, account.getLoginPwd());
            int affectedRows = ps.executeUpdate();

            // 삽입된 행이 있으면 성공, 없으면 실패
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error during signup: " + e.getMessage());
            return false;
        } finally {
            // 사용한 데이터베이스 자원 해제
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}