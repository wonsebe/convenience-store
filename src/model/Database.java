// Database.java (DTO)
// 데이터베이스 연결 설정 및 관리

package model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static Database instance;
    private Connection connection;
    private Properties properties;

    private Database() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/database.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // 싱글톤 패턴 전역접근 가능한 getInstance 메서드 (인스턴스 반환)
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}