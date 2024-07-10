package model.dao;

import model.dto.BoardDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDao {

    private static final BoardDao bDao = new BoardDao();
    // JDBC 인터페이스
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    private BoardDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/convenience_store", "root", "1234");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static BoardDao getInstance() {
        return bDao;
    }

    // 1. 전체출력
    public ArrayList<BoardDto> Bprinter() {
        ArrayList<BoardDto> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM board order by bmo desc";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bmo = rs.getInt("bmo");
                String bcontent = rs.getString("bcontent");
                String bdate = rs.getString("bdate");
                int store_id = rs.getInt("store_id");

                BoardDto boardDto = new BoardDto(bmo, bcontent, bdate, store_id);
                list.add(boardDto);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // 글쓰기
    public boolean addNotice(String content, int storeId) {
        try {
            String sql = "INSERT INTO board (bcontent, bdate, store_id) VALUES (?, CURDATE(), ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, content);
            ps.setInt(2, storeId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            System.out.println("게시물 작성 중 오류 발생: " + e);
            return false;
        }
    }

    public ArrayList<BoardDto> getAllNotices() {
        ArrayList<BoardDto> notices = new ArrayList<>();
        try {
            String sql = "SELECT * FROM board ORDER BY bmo DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bmo = rs.getInt("bmo");
                String bcontent = rs.getString("bcontent");
                String bdate = rs.getString("bdate");
                int store_id = rs.getInt("store_id");
                notices.add(new BoardDto(bmo, bcontent, bdate, store_id));
            }
        } catch (Exception e) {
            System.out.println("공지사항 조회 중 오류 발생: " + e);
        }
        return notices;
    }

} // BoardDao end
