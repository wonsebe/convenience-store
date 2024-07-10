package model.dao;

import model.dto.BoardDto;
import util.DbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDao {

    private static BoardDao bDao = new BoardDao();
    private BoardDao(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // com.mysql.cj.jdbc.Driver
            conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/convenience_store","root","1234" );

        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static BoardDao getInstance(){return bDao;}
    // JDBC 인터페이스
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    // 1. 전체출력
    public ArrayList<BoardDto> Bprinter(){
        ArrayList<BoardDto> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM board order by bmo desc";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int bmo = rs.getInt("bmo");
                String bcontent = rs.getString("bcontent");
                String bdate = rs.getString("bdate");
                String store_id = rs.getString("store_id");

                BoardDto boardDto = new BoardDto(bmo,bcontent,bdate,store_id);
                boardDto.setStore_id(rs.getString("store_id"));

                list.add(boardDto);
            }

        }catch (Exception e){System.out.println(e);
        }return list;
    }


    // 2. 게시물 쓰기
    public boolean Bwrite(String bcontent){
        try {


        }catch (Exception e){
            System.out.println(e);
        }
    }



} //BoardDao end
