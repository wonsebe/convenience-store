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
            String sql = "SELECT b.bmo, b.bcontent, b.bdate, s.id AS store_id, s.login_id " +
                    "FROM board b " +
                    "JOIN store s ON b.store_id = s.id " +
                    "ORDER BY b.bmo DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bmo = rs.getInt("bmo");
                String bcontent = rs.getString("bcontent");
                String bdate = rs.getString("bdate");
                int store_id = rs.getInt("store_id");
                String authorLoginId = rs.getString("login_id");

                // 생성자 호출 수정
                BoardDto boardDto = new BoardDto(bmo, bcontent, bdate, store_id, authorLoginId);
                list.add(boardDto);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // 글쓰기
    public boolean addNotice(String content, String authorLoginId) {
        try {
            String sql = "INSERT INTO board (bcontent, bdate, store_id) VALUES (?, CURDATE(), (SELECT id FROM store WHERE login_id = ?))";
            ps = conn.prepareStatement(sql);
            ps.setString(1, content);
            ps.setString(2, authorLoginId);
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
            String sql = "SELECT b.bmo, b.bcontent, b.bdate, s.id AS store_id, s.login_id " +
                    "FROM board b " +
                    "JOIN store s ON b.store_id = s.id " +
                    "ORDER BY b.bmo DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bmo = rs.getInt("bmo");
                String bcontent = rs.getString("bcontent");
                String bdate = rs.getString("bdate");
                int store_id = rs.getInt("store_id");
                String authorLoginId = rs.getString("login_id");

                // 생성자 호출 수정
                BoardDto boardDto = new BoardDto(bmo, bcontent, bdate, store_id, authorLoginId);
                notices.add(boardDto);
            }
        } catch (Exception e) {
            System.out.println("공지사항 조회 중 오류 발생: " + e);
        }
        return notices;
    }

} // BoardDao end
