package model.dao;

import model.dto.BoardDto;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDao {

    private static final BoardDao bDao = new BoardDao();

    private BoardDao() {
    }

    public static BoardDao getInstance() {
        return bDao;
    }

    public ArrayList<BoardDto> Bprinter() {
        ArrayList<BoardDto> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT * FROM board ORDER BY bmo DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bmo = rs.getInt("bmo");
                String bcontent = rs.getString("bcontent");
                String bdate = rs.getString("bdate");
                String store_id = rs.getString("store_id");

                BoardDto boardDto = new BoardDto(bmo, bcontent, bdate, store_id);
                boardDto.setStore_id(rs.getString("store_id"));

                list.add(boardDto);
            }
        } catch (SQLException e) {
            System.out.println("Error in Bprinter: " + e.getMessage());
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
        return list;
    }
} //BoardDao end