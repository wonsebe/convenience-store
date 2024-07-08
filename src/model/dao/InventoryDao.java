package model.dao;

import model.dto.InventoryLog;
import model.dto.Products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    // 턴넘기면 손님 방문 및 상품 구매 메서드
    public InventoryLog purchase(int productId, int quantity, int turn) {
        InventoryLog inventoryLog = null;
        try {
            // 상품 가격을 조회하기 위한 SQL 쿼리 준비
            String priceSql = "SELECT price FROM products WHERE product_id = ?";

            // PreparedStatement 객체 생성
            PreparedStatement pricePs = conn.prepareStatement(priceSql);

            // 쿼리의 첫 번째 파라미터(?)에 productId 값을 설정
            pricePs.setInt(1, productId);

            // 쿼리 실행 및 결과 저장
            // executeQuery()는 SELECT 문에 사용되며 ResultSet 객체를 반환.
            ResultSet priceRs = pricePs.executeQuery();

            // 가격을 저장할 변수 초기화
            int price = 0;

            // ResultSet에서 데이터 추출
            // next() 메소드는 다음 행으로 커서를 이동시키고, 행이 존재하면 true를 반환
            if (priceRs.next()) {
                // "price" 컬럼의 값을 정수형으로 가져와 price 변수에 저장
                price = priceRs.getInt("price");
            }

            // 재고 로그에 구매 기록 추가
            String sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description, sale_price) VALUES (?, ?, ?, '판매', ?)";

            // PreparedStatement 객체 생성
            // RETURN_GENERATED_KEYS를 사용하여 자동 생성된 키 값을 얻을 수 있게 설정
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // 쿼리 파라미터 설정
            ps.setInt(1, turn);  // 현재 게임 턴(날짜)
            ps.setInt(2, productId);  // 구매한 상품의 ID
            ps.setInt(3, -quantity);  // 구매 시 재고 감소 (음수 값으로 설정)
            ps.setInt(4, price * quantity);  // 총 판매 금액 계산

            // 쿼리 실행 및 영향받은 행의 수 반환
            // executeUpdate()는 INSERT, UPDATE, DELETE 문에 사용되며 영향받은 행의 수를 반환
            int affectedRows = ps.executeUpdate();

            // 쿼리 실행 결과 확인
            if (affectedRows > 0) {
                // 자동 생성된 키(logId) 가져오기
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    // 첫 번째 칼럼(자동 생성된 logId)의 값을 가져옴
                    int logId = rs.getInt(1);

                    // 새로운 InventoryLog 객체 생성
                    // 이 객체는 방금 삽입된 로그 정보를 담고 있음
                    inventoryLog = new InventoryLog(logId, turn, productId, -quantity, "판매", price * quantity);
                }
            }

        } catch (Exception e) {
            // 예외 발생 시 에러 메시지 출력
            System.out.println("구매 처리 중 오류 발생: " + e);
        }

        // 생성된 inventoryLog 객체 반환 (오류 발생 시 null일 수 있음)
        return inventoryLog;
    } // 턴넘기면 손님 방문 및 상품 구매 메서드 end

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

    // 전체 출력
    public ArrayList<Products> pPrint() {
        ArrayList<Products> list = new ArrayList<>();   // list 객체 생성 제품번호 제품명 제품가격 수량 유통기한 출력

        try {
            String sql = "select  product_id ,  name  , price  , expiry_turns  from products";   // 제품번호 , 이름 가격 , 수량 ,유통기한을 출력하는 sql
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) { // 조회된 레코드/행 만큼 반복 //  1개 행/레코드 -> 제품1개 -> products 1개
                int productid = rs.getInt("product_id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int expiryTurns = rs.getInt("expiry_turns");

                Products product = new Products();  // 302번지 객체 // 402번지 객체 // 502번지 객체
                product.setProductId(productid);
                product.setName(name);
                product.setPrice(price);
                product.setExpiryTurns(expiryTurns);

                list.add(product); // 302번지 객체  // 402번지 객체 // 502번지 객체

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;

    }
    //강도함수
    public void inrush(int productId, int quantity) {
        try {
            // 감소할 수량을 음수로 저장 (로그 기록)
            String sql = "INSERT INTO inventory_log(game_date, product_id, quantity, description) " +
                    "VALUES (0, ?, ?, '강도에 의한 재고 감소')";
            //
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, -quantity);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("강도 침입 처리 중 오류 발생: " + e);
        }
    }

    // 재고 구하기
    public int stock(int product_id){
        try {
            String sql = "select * from inventory_log where product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, product_id);
            rs = ps.executeQuery();
            int sum = 0;
            while (rs.next()){
                sum += rs.getInt(4);
            }
            return sum;
        }catch (Exception e){
            System.out.println(e);
        }return 0;
    }

}
