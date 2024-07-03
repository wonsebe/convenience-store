package project.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class Dao {

    // -- tldrmfxhs
    // private static 변수에 해당 클래스의 객체 생성해서 대입
    private static Dao dao = new Dao();
    // 해당 클래스의 생성자를 private 한다. 다른 클래스로부터 new 사용하지 못하게 차단하기
    private Dao(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // com.mysql.cj.jdbc.Driver
            conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/test","root","1234" );
        }catch (Exception e){
            System.out.println(e);
        }
    }
    // 해당 객체(싱글톤)를 외부로부터 접근할 수 있도록
    public static Dao getInstance(){return dao;}

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    // 구매
    public boolean 구매( project.model.ProductDto productDto  ){

        try{
            String sql = "insert into inventory_log(game_date, product_id, quantity, description) " +
                    " values( ? , ? , ? , 'Sales')";
            ps = conn.prepareCall(sql);
            ps.setInt( 1 , productDto.getGame_date());
            ps.setInt( 2 , productDto.getpNum());
            ps.setInt( 3, productDto.getQuantity() );
            ps.setString( 4, productDto.getDescription() );
            ps.executeUpdate();

        }catch (Exception e ){
            System.out.println(e);
        }
        return false;
    }
    // 재고 확인
    public int 개별재고확인( int pNum ){
        try{
            String sql = "select * from inventory_log where product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt( 1 , pNum );
            rs = ps.executeQuery();
            int sum = 0 ;
            while ( rs.next() ){
                sum += rs.getInt( 4 );
            }
            return sum;
        }catch (Exception e ){ }
        return 0;
    }

}
