package model.dao;

import controller.PcController;
import model.dto.InventoryLog;
import model.dto.Products;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;

// 편의점 재고 관리를 위한 Data Access Object (DAO) 클래스
// 재고 확인, 구매, 삭제 등
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
    private ProductDao productDao;


    // 생성자 (private으로 외부에서 인스턴스 생성 방지)
    private InventoryDao() {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 등록된 멤버변수를 사용해 데이터베이스 연결
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // ProductDao 초기화
            productDao = ProductDao.getInstance();
        } catch (Exception e) {
            System.out.println("데이터베이스 연결 중 오류 발생: " + e);
        }
    } // 생성자 메서드 end

    // 싱글톤 인스턴스 반환 메서드
    public static InventoryDao getInstance() {
        return iDao;
    }

    public void supplyRestock(int pId, int quantity, int turn, int storeId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description, purchase_date, store_id) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, turn);
            ps.setInt(2, pId);
            ps.setInt(3, quantity);
            ps.setString(4, "재고 입고");
            ps.setInt(5, turn);
            ps.setInt(6, storeId);
            ps.executeUpdate();
        } finally {
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    } // 1 - 재고 구매 메서드 end

    public boolean hasInitialInventory(int storeId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM inventory_log WHERE store_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, storeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("초기 재고 확인 중 오류 발생: " + e.getMessage());
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return false;
    }

    public void initializeInventory(int storeId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            conn.setAutoCommit(false);

            // 먼저 해당 store_id가 존재하는지 확인
            String checkStoreSql = "SELECT id FROM store WHERE id = ?";
            ps = conn.prepareStatement(checkStoreSql);
            ps.setInt(1, storeId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new SQLException("오류 store ID: " + storeId);
            }

            // 이미 재고가 있는지 확인
            String checkInventorySql = "SELECT COUNT(*) FROM inventory_log WHERE store_id = ?";
            ps = conn.prepareStatement(checkInventorySql);
            ps.setInt(1, storeId);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("이미 초기 재고가 설정되어 있습니다.");
                return;
            }

            // 초기 재고 설정
            String sql = "INSERT INTO inventory_log (game_date, product_id, quantity, description, store_id) " +
                    "SELECT 1, product_id, 20, '초기 입고', ? FROM products";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, storeId);
            int rowsAffected = ps.executeUpdate();
            conn.commit();
            System.out.println(rowsAffected + "개의 상품에 대한 초기 재고가 설정되었습니다.");
        } catch (SQLException e) {
            System.out.println("초기 재고 설정 중 오류 발생: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("롤백 중 오류 발생: " + ex.getMessage());
                }
            }
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }

    // 유통기한이 지난 재고를 폐기하는 메서드
    public void removeExpiredInventory(int currentTurn) {
        try {
            String sql = "DELETE FROM inventory_log WHERE product_id IN " +
                    "(SELECT product_id FROM products WHERE expiry_turns + game_date <= ?) AND store_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, currentTurn);
            ps.setInt(2, PcController.getInstance().getCurrentStoreId());
            int removedCount = ps.executeUpdate();
            if (removedCount > 0) {
                System.out.println(removedCount + "개의 유통기한 지난 상품이 폐기되었습니다.");
            }
        } catch (Exception e) {
            System.out.println("유통기한 지난 재고 제거 중 오류 발생: " + e);
        }
    }

    // 턴넘기면 손님 방문 및 상품 구매 메서드
    public InventoryLog purchase(int productId, int quantity, int turn, int storeId) throws SQLException {
        InventoryLog inventoryLog = null;
        String sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description, sale_price, purchase_date, store_id) VALUES (?, ?, ?, '판매', ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, turn);
            ps.setInt(2, productId);
            ps.setInt(3, -quantity);
            int price = productDao.getProductPrice(productId);
            ps.setInt(4, price * quantity);
            ps.setInt(5, turn);
            ps.setInt(6, storeId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int logId = rs.getInt(1);
                        inventoryLog = new InventoryLog(logId, turn, productId, -quantity, "판매", price * quantity, storeId);
                    }
                }
            }
        }
        return inventoryLog;
    } // 턴넘기면 손님 방문 및 상품 구매 메서드 end

    // 재고 확인 메서드
    public int checkInventory(int productId) {
        int sum = 0;
        try {
            String sql = "SELECT SUM(quantity) as total FROM inventory_log WHERE product_id = ? AND store_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, PcController.getInstance().getCurrentStoreId());
            rs = ps.executeQuery();
            if (rs.next()) {
                sum = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("재고 확인 중 오류 발생: " + e);
        }
        return sum;
    } // 재고 확인 메서드 end

    // 재고 삭제 메서드
    public boolean pdelete(int productId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            // products 테이블에서 해당 product_id를 가진 상품을 삭제하는 SQL 쿼리 준비
            String sql = "DELETE FROM products WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            int count = ps.executeUpdate();

            if (count == 1) {
                // 상품이 성공적으로 삭제되었다면 관련된 재고 로그도 삭제
                sql = "DELETE FROM inventory_log WHERE product_id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, productId);
                ps.executeUpdate();

                return true;
            }
        } catch (Exception e) {
            System.out.println("재고 삭제 중 오류 발생: " + e);
        } finally {
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return false;
    } // 재고 삭제 메서드 end

    // 전체 출력
    public ArrayList<Products> pPrint() {
        ArrayList<Products> list = new ArrayList<>();
        try {
            String sql = "SELECT p.product_id, p.name, p.price, p.expiry_turns, COALESCE(SUM(il.quantity), 0) as stock " +
                    "FROM products p " +
                    "LEFT JOIN inventory_log il ON p.product_id = il.product_id AND il.store_id = ? " +
                    "GROUP BY p.product_id, p.name, p.price, p.expiry_turns";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, PcController.getInstance().getCurrentStoreId());
            rs = ps.executeQuery();

            while (rs.next()) {
                Products product = new Products();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                product.setExpiryTurns(rs.getInt("expiry_turns"));
                product.setStock(rs.getInt("stock"));
                list.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    //강도함수
    public void inrush(int productId, int quantity) {
        try {
            // 제품이 존재하는지 확인
            int currentStoreId = PcController.getInstance().getCurrentStoreId();
            int existingProductCount = ProductDao.getInstance().getProductTypeCount();
            if (existingProductCount <= 0) {
                System.out.println("해당 상품이 존재하지 않아 강도 침입 처리를 할 수 없습니다.");
                return;
            }

            // 감소할 수량을 음수로 저장 (로그 기록)
            String sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description, store_id) " +
                    "VALUES (0, ?, ?, '강도에 의한 재고 감소', ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, -quantity);
            ps.setInt(3, currentStoreId);
            ps.executeUpdate();

            // 현재 재고 출력
            int currentStock = checkInventory(productId);
            System.out.println("강도가 " + ProductDao.getInstance().getProductName(productId) + "을(를) " + quantity + "개 훔쳐갔습니다. (남은 재고: " + currentStock + ")");
        } catch (Exception e) {
            System.out.println("강도 침입 처리 중 오류 발생: " + e);
        }
    }

    // 재고 구하기
    public int stock(int product_id) {
        try {
            String sql = "SELECT SUM(quantity) as total FROM inventory_log WHERE product_id = ? AND store_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, product_id);
            ps.setInt(2, PcController.getInstance().getCurrentStoreId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}