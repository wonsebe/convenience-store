package model.dao;

import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameSaveDao {
    private static final GameSaveDao gameSaveDao = new GameSaveDao();

    private GameSaveDao() {
    }

    public static GameSaveDao getInstance() {
        return gameSaveDao;
    }

    public boolean saveGame(String loginId, int currentTurn) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE store SET current_turn = ? WHERE login_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, currentTurn);
            ps.setString(2, loginId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error saving game: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }

    public int loadGame(String loginId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT current_turn FROM store WHERE login_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, loginId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("current_turn");
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return -1;
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}