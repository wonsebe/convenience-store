package model.dao;

import model.dto.AccountDto;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupDao {
    private static final SignupDao signupDao = new SignupDao();

    private SignupDao() {
    }

    public static SignupDao getInstance() {
        return signupDao;
    }

    public boolean signup(AccountDto account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();

            String checkSql = "SELECT COUNT(*) FROM store WHERE login_id = ?";
            ps = conn.prepareStatement(checkSql);
            ps.setString(1, account.getLoginId());
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Username already exists.");
                return false;
            }

            String insertSql = "INSERT INTO store (login_id, login_pwd, current_turn) VALUES (?, ?, 1)";
            ps = conn.prepareStatement(insertSql);
            ps.setString(1, account.getLoginId());
            ps.setString(2, account.getLoginPwd());
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error during signup: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}