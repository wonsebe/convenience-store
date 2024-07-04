package model.dao;

import model.dto.InventoryLog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryDao {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final InventoryDao iDao = new InventoryDao();

    // 데이터베이스 연결을 위한 객체들
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // 생성자 (private으로 외부에서 인스턴스 생성 방지)
    private InventoryDao() {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 데이터베이스 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/convenience_store", "root", "1234");
        } catch (Exception e) {
            System.out.println("데이터베이스 연결 중 오류 발생: " + e);
        }
    } // 생성자 메서드 end

    // 싱글톤 인스턴스 반환 메서드
    public static InventoryDao getInstance() {
        return iDao;
    }

    // 상품 구매 메서드
    public InventoryLog purchase(int productId, int quantity, int turn) {
        InventoryLog inventoryLog = null;
        try {
            // SQL 쿼리 준비 (재고 로그에 구매 기록 추가)
            String sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description) " +
                    "VALUES (?, ?, ?, '판매')";
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, turn);
            ps.setInt(2, productId);
            ps.setInt(3, -quantity);  // 구매 시 재고 감소 (음수 값으로 설정)
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                // 삽입된 로그의 ID 가져오기
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int logId = rs.getInt(1);
                    // InventoryLog 객체 생성
                    inventoryLog = new InventoryLog(logId, turn, productId, -quantity, "판매");
                }
            }
        } catch (Exception e) {
            System.out.println("구매 처리 중 오류 발생: " + e);
        }
        return inventoryLog;
    } // 상품 구매 메서드 end

    // 재고 확인 메서드
    public int checkInventory(int productId) {
        int sum = 0;
        try {
            // SQL 쿼리 준비 (특정 상품의 총 재고량 계산)
            String sql = "SELECT SUM(quantity) as total FROM inventory_log WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                sum = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("재고 확인 중 오류 발생: " + e);
        }
        return sum;
    } // 재고 확인 메서드 end
}