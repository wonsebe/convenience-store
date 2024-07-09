package util;

import java.sql.*;

public class DbUtil {
    // 데이터베이스 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/convenience_store";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    // 데이터베이스 연결을 생성하고 반환하는 메서드
    public static Connection getConnection() throws SQLException {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 데이터베이스 연결 생성 및 반환
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            // 드라이버를 찾지 못한 경우 SQLException으로 변환하여 throw
            throw new SQLException("MySQL JDBC 드라이버를 찾을 수 없습니다.", e);
        }
    }

    // Statement 객체를 안전하게 닫는 메서드
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("데이터베이스 연결 종료 중 오류 발생: " + e.getMessage());
            }
        }
    }

    // Statement 객체를 안전하게 닫는 메서드
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Statement 종료 중 오류 발생: " + e.getMessage());
            }
        }
    }

    // ResultSet 객체를 안전하게 닫는 메서드
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("ResultSet 종료 중 오류 발생: " + e.getMessage());
            }
        }
    }
}