package model.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Dao {
    // private static 변수에 해당 클래스의 객체 생성해서 대입
    private static final Dao dao = new Dao();
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    // 해당 클래스의 생성자를 private 한다. 다른 클래스로부터 new 사용하지 못하게 차단하기
    private Dao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // com.mysql.cj.jdbc.Driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/convenience_store", "root", "1234");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 해당 객체(싱글톤)를 외부로부터 접근할 수 있도록
    public static Dao getInstance() {
        return dao;
    }

    // 구매
    public boolean 구매(int pNum, int pCount, int 턴수) {

        try {
            String sql = "insert into inventory_log(game_date, product_id, quantity, description) " +
                    " values( ? , ? , ? , 'Sales')";
            ps = conn.prepareCall(sql);
            ps.setInt(1, 턴수);
            ps.setInt(2, pNum);
            ps.setInt(3, -pCount);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // 재고 확인
    public int 개별재고확인(int pNum) {
        try {
            String sql = "select * from inventory_log where product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, pNum);
            rs = ps.executeQuery();
            int sum = 0;
            while (rs.next()) {
                sum += rs.getInt(4);
            }
            return sum;
        } catch (Exception e) {
        }
        return 0;
    }

}
