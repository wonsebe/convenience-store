package model.dao;

import controller.PcController;
import util.DbUtil;

import java.sql.*;

// 편의점 잔고 관리를 위한 Data Access Object (DAO) 클래스
// 싱글톤 패턴을 사용해 구현, 데이터베이스와의 연결 및 잔고 관련 작업 담당
public class StoreDao {
    // 싱글톤 인스턴스
    private static final StoreDao storeDao = new StoreDao();
    // 데이터베이스 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/convenience_store";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    // 데이터베이스 연결 객체
    private Connection conn;

    // 생성자, 데이터베이스 연결 초기화
    private StoreDao() {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 데이터베이스 연결
            this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC 드라이버를 찾을 수 없습니다: " + e);
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 실패: " + e);
        }
    }

    // StoreDao의 싱글톤 인스턴스 반환
    public static StoreDao getInstance() {
        return storeDao;
    }

    // 현재 편의점의 잔고를 조회하는 메서드
    public int getBalance() {
        int balance = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 데이터베이스 연결 생성
            conn = DbUtil.getConnection();
            // SQL 쿼리 준비, store_balance 테이블에서 현재 로그인된 사용자의 잔고를 조회
            String sql = "SELECT balance FROM store WHERE login_id = ?";
            // SQL 쿼리를 실행하기 위해 PreparedStatement 객체를 생성
            ps = conn.prepareStatement(sql);
            // 현재 로그인된 사용자 ID를 SQL 쿼리의 첫 번째 매개변수에 설정
            ps.setString(1, PcController.getInstance().getCurrentLoginId());
            // 쿼리를 실행하고 결과를 ResultSet 객체로 반환
            rs = ps.executeQuery();
            // ResultSet 객체에서 다음 행(row)을 가져옴. 첫 번째 행이 존재하면 true를 반환
            if (rs.next()) {
                // balance 열의 값을 가져와서 balance 변수에 저장
                balance = rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println("잔고 조회 중 오류 발생: " + e.getMessage());
        } finally {
            // 리소스 해제 (ResultSet, PreparedStatement, Connection 객체)
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return balance; // 조회한 잔고 반환
    }

    // 로그인 ID를 이용, store ID를 조회하는 메서드
    public int getStoreIdByLoginId(String loginId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT id FROM store WHERE login_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, loginId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Store ID 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return 0;
    }

    // 편의점의 잔고를 업데이트
    public boolean updateBalance(int balance, int turn, int storeId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            // SQL 쿼리 수정: store_id를 사용하여 특정 편의점의 잔고를 업데이트
            String sql = "UPDATE store SET balance = ?, current_turn = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, balance);
            ps.setInt(2, turn);
            ps.setInt(3, storeId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("잔고 업데이트 중 오류 발생: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}