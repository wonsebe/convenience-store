package model.dao;

import model.dto.AccountDto;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    private static final LoginDao loginDao = new LoginDao();

    private LoginDao() {
    }

    public static LoginDao getInstance() {
        return loginDao;
    }

    public boolean login(AccountDto account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT * FROM store WHERE login_id = ? AND login_pwd = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getLoginId());
            ps.setString(2, account.getLoginPwd());
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}