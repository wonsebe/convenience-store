package model.dao;

import model.dto.InventoryLog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryDao {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final InventoryDao iDao = new InventoryDao();
    // 데이터베이스 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/convenience_store";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    // 데이터베이스 연결을 위한 객체들
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // 생성자 (private으로 외부에서 인스턴스 생성 방지)
    private InventoryDao() {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 등록된 멤버변수를 사용해 데이터베이스 연결
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
            String sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description) " + "VALUES (?, ?, ?, '판매')";
            // RETURN_GENERATED_KEYS: 새로운 행을 삽입할 때 자동 생성된
            // 키(auto_increment 설정된 primary key)를 반환받기 위해 사용
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, turn);
            ps.setInt(2, productId);
            ps.setInt(3, -quantity);  // 구매 시 재고 감소 (음수 값으로 설정)
            // executeUpdate()
            // INSERT, UPDATE, DELETE와 같이 데이터베이스의 상태를 변경하는 SQL 문에 사용
            // 영향받은 행의 수를 정수로 반환.
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
            String sql = "SELECT SUM(quantity) as total FROM inventory_log WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            // executeQuery()
            // SELECT 문과 같이 데이터를 조회하는 SQL 문에 사용합니다.
            // 결과를 ResultSet 객체로 반환
            rs = ps.executeQuery();
            if (rs.next()) {
                sum = rs.getInt("total");
            }
            // System.out.println("상품 ID " + productId + "의 현재 재고: " + sum); // 디버깅을 위한 출력
        } catch (Exception e) {
            System.out.println("재고 확인 중 오류 발생: " + e);
        }
        return sum;
    } // 재고 확인 메서드 end

    // 재고 삭제 메서드
    public boolean pdelete(int productId) {
        try {
            // products 테이블에서 해당 product_id를 가진 상품을 삭제하는 SQL 쿼리 준비
            String sql = "DELETE FROM products WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            // executeUpdate()
            // DELETE 문은 데이터베이스의 상태를 변경하므로 executeUpdate() 사용.
            // 영향받은 행의 수를 반환합니다.
            int count = ps.executeUpdate();

            if (count == 1) {
                // 상품이 성공적으로 삭제되었다면 관련된 재고 로그도 삭제
                sql = "DELETE FROM inventory_log WHERE product_id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, productId);

                // executeUpdate()
                // DELETE 문은 데이터베이스의 상태를 변경하므로 executeUpdate()를 사용
                ps.executeUpdate();

                return true;
            }
        } catch (Exception e) {
            System.out.println("재고 삭제 중 오류 발생: " + e);
        }
        return false;
    } // 재고 삭제 메서드 end
}