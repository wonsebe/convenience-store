package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDao {
    // ProductDao 클래스의 싱글톤 인스턴스
    private static final ProductDao pDao = new ProductDao();

    // 데이터베이스 연결 객체
    private Connection conn;

    // SQL 쿼리 실행을 위한 객체
    private PreparedStatement ps;

    // 쿼리 실행 결과를 저장하기 위한 객체
    private ResultSet rs;

    // 생성자를 private로 하여 외부에서 인스턴스 생성 차단
    private ProductDao() {
        try {
            // MySQL 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 데이터베이스 연결 설정
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/convenience_store", "root", "1234");
        } catch (Exception e) {
            // 예외 발생 시 예외 메시지 출력
            System.out.println(e);
        }
    }

    // 싱글톤 인스턴스에 접근할 수 있는 메서드
    public static ProductDao getInstance() {
        return pDao;
    }
}
