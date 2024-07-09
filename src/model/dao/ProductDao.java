package model.dao;

import model.dto.Products;
import util.DbUtil;

import java.sql.*;

// 편의점 상품 관리를 위한 Data Access Object (DAO) 클래스
// 상품 정보 조회, 추가, 수정 등
public class ProductDao {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final ProductDao pDao = new ProductDao();

    // 생성자 (private으로 외부에서 인스턴스 생성 방지)
    private ProductDao() {
    } // 생성자 end

    // 싱글톤 인스턴스 반환 메서드
    public static ProductDao getInstance() {
        return pDao;
    } // 싱글톤 인스턴스 반환 메서드 end

    // 요청한 상품의 가격을 반환하는 메서드
    public int getProductPrice(int productId) {
        int price = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT price FROM products WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                price = rs.getInt("price");
            }
        } catch (SQLException e) {
            System.out.println("상품 가격 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return price;
    }

    // 상품 이름 조회 메서드
    public String getProductName(int productId) {
        String name = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            // SQL 쿼리 준비 (상품 ID로 상품 이름 조회)
            String sql = "SELECT name FROM products WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            // executeQuery()
            // SELECT 문과 같이 데이터를 조회하는 SQL 문에 사용합니다.
            // 결과를 ResultSet 객체로 반환
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("상품 이름 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return name;
    } // 상품 이름 조회 메서드 end

    // 등록된 상품 종류의 수를 반환하는 메서드
    public int getProductTypeCount() {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            // SQL 쿼리 준비 (등록된 상품 종류의 총 개수 조회)
            String sql = "SELECT COUNT(DISTINCT product_id) as count FROM products";
            ps = conn.prepareStatement(sql);
            // executeQuery()
            // SELECT 문과 같이 데이터를 조회하는 SQL 문에 사용합니다.
            // 결과를 ResultSet 객체로 반환
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("등록된 상품 종류 수 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return count;
    } // 등록된 상품 종류의 수를 반환하는 메서드 end

    // 상품 추가 메서드
    public boolean add(Products products) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            // 트랜잭션 처리 추가, why?
            // 두 개의 INSERT 작업이 모두 성공하거나 모두 실패하도록 보장함
            conn.setAutoCommit(false);  // 트랜잭션 시작
            String sql = "INSERT INTO products(name, price, expiry_turns) VALUES(?, ?, ?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, products.getName());
            ps.setInt(2, products.getPrice());
            ps.setInt(3, products.getExpiryTurns());
            // executeUpdate()
            // INSERT, UPDATE, DELETE와 같이 데이터베이스의 상태를 변경하는 SQL 문에 사용
            // 영향받은 행의 수를 정수로 반환.
            int count = ps.executeUpdate();

            if (count == 1) {
                // 생성된 product_id 가져오기
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int productId = rs.getInt(1);
                    DbUtil.closeStatement(ps);  // 이전 PreparedStatement 닫기
                    // 초기 재고 추가 (예: 10개)
                    sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description) VALUES(0, ?, 10, '초기 입고')";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, productId);
                    // executeUpdate()
                    // INSERT, UPDATE, DELETE와 같이 데이터베이스의 상태를 변경하는 SQL 문에 사용
                    // 영향받은 행의 수를 정수로 반환.
                    ps.executeUpdate();

                    conn.commit();  // 트랜잭션 커밋
                    return true;
                }
            }
            conn.rollback();  // 실패 시 롤백
            return false;
        } catch (SQLException e) {
            System.out.println("상품 추가 중 오류 발생: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();  // 오류 발생 시 롤백
                } catch (SQLException ex) {
                    System.out.println("롤백 중 오류 발생: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);  // auto-commit 모드 복원
                } catch (SQLException e) {
                    System.out.println("auto-commit 모드 복원 중 오류 발생: " + e.getMessage());
                }
            }
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    } // 상품 추가 메서드 end

    // 물품 수정 메서드
    public boolean pUpdate(Products products) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE products SET price = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, products.getPrice());
            ps.setInt(2, products.getProductId());

            // executeUpdate()
            // INSERT, UPDATE, DELETE와 같이 데이터베이스의 상태를 변경하는 SQL 문에 사용
            // 영향받은 행의 수를 정수로 반환.
            int count = ps.executeUpdate();
            return count == 1;
        } catch (SQLException e) {
            System.out.println("상품 수정 중 오류 발생: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    } // 물품 수정 메서드 end
}