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
            conn = DbUtil.getConnection();

            // 이미 존재하는 사용자명인지 확인
            String checkSql = "SELECT COUNT(*) FROM store WHERE login_id = ?";
            ps = conn.prepareStatement(checkSql);
            // SQL 쿼리의 첫 번째 ?를 로그인 ID로 설정
            ps.setString(1, account.getLoginId());
            // 쿼리 실행 후 결과를 ResultSet 객체로 반환
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // 결과가 존재하고, 해당 ID로 등록된 계정이 이미 존재하는 경우
                System.out.println("이미 존재하는 아이디입니다.");
                return false;
            }

            // 새로운 계정 정보 삽입
            String insertSql = "INSERT INTO store (login_id, login_pwd, current_turn, balance) VALUES (?, ?, 1, 1000000)";
            ps = conn.prepareStatement(insertSql);
            // SQL 쿼리의 첫 번째 ?를 로그인 ID로 설정
            ps.setString(1, account.getLoginId());
            // SQL 쿼리의 두 번째 ?를 로그인 비밀번호로 설정
            ps.setString(2, account.getLoginPwd());
            // 쿼리 실행 후 영향을 받은 행의 수를 반환
            int affectedRows = ps.executeUpdate();

            // 영향받은 행의 수가 0보다 크면 회원가입 성공
            return affectedRows > 0;
        } catch (SQLException e) {
            // SQL 예외 발생 시 오류 메시지 출력
            System.out.println("회원가입 중 오류 발생: " + e.getMessage());
            return false; // 회원가입 실패
        } finally {
            // ResultSet, PreparedStatement, Connection 객체를 안전하게 닫음
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}