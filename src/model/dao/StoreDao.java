package model.dao;

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
            conn = DbUtil.getConnection();
            String sql = "SELECT balance FROM store_balance WHERE store_id = (SELECT id FROM store WHERE login_id = ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "admin"); // 실제로는 현재 로그인된 사용자 ID를 사용해야 함
            rs = ps.executeQuery();
            if (rs.next()) {
                balance = rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println("잔고 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return balance;
    }


    // 편의점의 잔고를 업데이트
    public boolean updateBalance(int balance, int turn) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE store_balance SET balance = ? WHERE store_id = (SELECT id FROM store WHERE login_id = ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, balance);
            ps.setString(2, "admin"); // 실제로는 현재 로그인된 사용자 ID를 사용해야 함
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