package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDao {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final ProductDao pDao = new ProductDao();

    // 데이터베이스 연결을 위한 객체들
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // 생성자 (private으로 외부에서 인스턴스 생성 방지)
    private ProductDao() {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 데이터베이스 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/convenience_store", "root", "1234");
        } catch (Exception e) {
            System.out.println("데이터베이스 연결 중 오류 발생: " + e);
        }
    } // 생성자 end

    // 싱글톤 인스턴스 반환 메서드
    public static ProductDao getInstance() {
        return pDao;
    } // 싱글톤 인스턴스 반환 메서드 end

    // 상품 이름 조회 메서드
    public String getProductName(int productId) {
        String name = null;
        try {
            // SQL 쿼리 준비 (상품 ID로 상품 이름 조회)
            String sql = "SELECT name FROM products WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (Exception e) {
            System.out.println("상품 이름 조회 중 오류 발생: " + e);
        }
        return name;
    } // 상품 이름 조회 메서드 end

    // 등록된 상품 종류의 수를 반환하는 메서드
    public int getProductTypeCount() {
        int count = 0;
        try {
            // SQL 쿼리 준비 (등록된 상품 종류의 총 개수 조회)
            String sql = "SELECT COUNT(DISTINCT product_id) as count FROM products";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("등록된 상품 종류 수 조회 중 오류 발생: " + e);
        }
        return count;
    } // 등록된 상품 종류의 수를 반환하는 메서드 end
}