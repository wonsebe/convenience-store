package model.dao;

import model.dto.InventoryLog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

// 편의점 매출 관리를 위한 Data Access Object (DAO) 클래스
// 싱글톤 패턴을 사용해 구현, 데이터베이스와의 연결 및 매출 관련 작업 담당
public class SalesDao {
    private static final SalesDao salesDao = new SalesDao();
    private Connection conn;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/convenience_store";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    // 생성자, 데이터베이스 연결 초기화
    private SalesDao() {
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

    // SalesDao의 싱글톤 인스턴스를 반환
    public static SalesDao getInstance() {
        return salesDao;
    }

    /**
     * 제공된 재고 로그 목록을 기반으로 총 매출액을 계산합니다.
     *
     * @param logs 재고 로그 목록
     */
    public int calculateTotalSales(ArrayList<InventoryLog> logs) {
        // 총 매출액을 저장할 변수를 0으로 초기화
        int totalSales = 0;

        // 재고 로그 목록을 순회하면서 각 로그의 판매 금액을 더함
        for (InventoryLog log : logs) {
            // 각 로그의 판매 금액을 총 매출액에 더함
            totalSales += log.getSalePrice();
        }
        // 계산된 총 매출액 반환
        return totalSales;
    }

    /**
     * 특정 턴의 매출 정보를 데이터베이스에 저장합니다.
     *
     * @param turn       현재 게임 턴
     * @param totalSales 해당 턴의 총 매출액
     */
    public void saveSales(int turn, int totalSales) {
        if (conn == null) {
            System.out.println("데이터베이스 연결이 설정되지 않았습니다.");
            return;
        }
        try {
            // 매출 정보를 삽입하는 SQL 쿼리
            String sql = "INSERT INTO sales(game_date, total_sales, profit) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, turn);
            ps.setInt(2, totalSales);
            ps.setInt(3, totalSales); // 간단히 하기 위해 profit을 total_sales와 같게 설정
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("매출 저장 중 오류 발생: " + e);
        }
    }
}
