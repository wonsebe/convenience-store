package model.dao;

import com.sun.jdi.event.ExceptionEvent;
import model.dto.Products;

import model.dto.Products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InventoryDao {
    // InventoryDao 클래스의 싱글톤 인스턴스
    private static final InventoryDao iDao = new InventoryDao();


    // 데이터베이스 연결 객체
    private Connection conn;

    // SQL 쿼리 실행을 위한 객체
    private PreparedStatement ps;

    // 쿼리 실행 결과를 저장하기 위한 객체
    private ResultSet rs;

    // 생성자를 private로 하여 외부에서 인스턴스 생성 차단
    private InventoryDao() {
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
    public static InventoryDao getInstance() {
        return iDao;
    }

    // 구매 메서드
    public boolean purchase(int productId, int quantity, int turn) {
        try {
            // 재고 로그 테이블에 구매 기록 추가
            String sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description) " +
                    "VALUES (?, ?, ?, 'Sales')";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, turn); // 턴 수 설정
            ps.setInt(2, productId); // 제품 ID 설정
            ps.setInt(3, -quantity);  // 구매 시 재고 감소 (음수 값으로 설정)
            ps.executeUpdate(); // 쿼리 실행
            return true; // 성공 시 true 반환
        } catch (Exception e) {
            // 예외 발생 시 예외 메시지 출력
            System.out.println(e);
        }
        return false; // 실패 시 false 반환
    }

    // 재고 확인 메서드
    public int checkInventory(int productId) {
        try {
            // 특정 제품의 재고를 확인하는 쿼리
            String sql = "SELECT * FROM inventory_log WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId); // 제품 ID 설정
            rs = ps.executeQuery(); // 쿼리 실행

            // 재고 수량을 누적 합산하기 위한 변수
            int sum = 0;
            while (rs.next()) {
                sum += rs.getInt("quantity"); // 각 행의 수량을 누적 합산
            }
            return sum; // 총 재고 수량 반환
        } catch (Exception e) {
            // 예외 발생 시 예외 메시지 출력
            System.out.println(e);
        }
        return 0; // 예외 발생 시 0 반환
    }

    public boolean pdelete(int productId) {
        try {
            String sql="delete from products where product_id=?";
            ps=conn.prepareStatement(sql);
            System.out.println(sql);
            ps.setInt(1,productId);
            int count=ps.executeUpdate();
            if (count==1)return true;

        }catch (Exception e){
            System.out.println(e);
        }return false;

    }

    public boolean add(Products products) {

        try {

            String sql="INSERT INTO products( product_Id , name , price,expiry_Turns ) VALUES( ? , ? , ?, ?  )";
            System.out.println("sql = " + sql);
            ps=conn.prepareStatement(sql);
            ps.setInt(1,products.getProductId());
            ps.setString(2,products.getName());
            ps.setInt(3,products.getPrice());
            ps.setInt(4,products.getExpiryTurns());
            int count=ps.executeUpdate();
            if (count==1){
                return  true;
            }



        }catch (Exception e){
            System.out.println(e);
        }return false;
    }
    // 전체 출력
    public ArrayList<Products> pPrint(){
        ArrayList list = new ArrayList<>();

        try {
        String sql = "select product_id ,  name  , price  from products";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()){
            int productid = rs.getInt("product_id");
            String name = rs.getString("name");
            int price = rs.getInt( "price");

            Products product = new Products();
            product.setProductId(productid);
            product.setName(name);
            product.setPrice(price);

            list.add(product);
        }
        }catch (Exception e){
            System.out.println(e);
        }return list;

    }




    // 물품 수정
    public boolean pUpdate(Products products) {
        try {
            String sql = "update produuts set price = ? where product_id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,products.getPrice() );
            ps.setInt(2,products.getProductId());

            int count = ps.executeUpdate();
            if (count == 1) return true;
        }catch (Exception e){
            System.out.println(e);
        }return false;
    }

}
