package model.dao;

import java.sql.*;

public class StoreDao {
    private static final StoreDao storeDao = new StoreDao();
    private Connection conn;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/convenience_store";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    private StoreDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC 드라이버를 찾을 수 없습니다: " + e);
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 실패: " + e);
        }
    }

    public static StoreDao getInstance() {
        return storeDao;
    }

    public int getBalance() {
        int balance = 0;
        if (conn == null) {
            System.out.println("데이터베이스 연결이 설정되지 않았습니다.");
            return balance;
        }
        try {
            String sql = "SELECT balance FROM store_balance ORDER BY game_turn DESC LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getInt("balance");
                }
            }
        } catch (SQLException e) {
            System.out.println("잔고 조회 중 오류 발생: " + e);
        }
        return balance;
    }

    public void updateBalance(int amount, int turn) {
        if (conn == null) {
            System.out.println("데이터베이스 연결이 설정되지 않았습니다.");
            return;
        }
        try {
            String sql = "INSERT INTO store_balance (balance, game_turn) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, amount);
                ps.setInt(2, turn);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("잔고 업데이트 중 오류 발생: " + e);
        }
    }
}