// Database.java (DTO)
// 데이터베이스 연결 설정 및 관리

package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {
    private static Database instance;
    private Connection connection;
    private Properties properties;

    private Database() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/database.properties")) {
            if (input == null) {
                System.out.println("config/database.properties 파일을 찾을 수 없습니다.");
                return;
            }
            properties.load(input);

            // JDBC 드라이버 불러오기
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버 클래스 직접 명시
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC 드라이버를 찾을 수 없습니다: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("프로퍼티 파일 로드 중 오류 발생: " + e.getMessage());
        }
    }

    // 싱글톤
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
            if (url == null || user == null || password == null) {
                throw new SQLException("데이터베이스 연결 정보가 없습니다.");
            }
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public void initializeDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC 드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC 드라이버를 찾을 수 없습니다: " + e.getMessage());
            return;
        }

        try {
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            String baseUrl = "jdbc:mysql://localhost:3306";

            try (Connection conn = DriverManager.getConnection(baseUrl, user, password);
                 Statement stmt = conn.createStatement()) {

                // 데이터베이스 생성 (존재하지 않는 경우)
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS convenience_store");
                System.out.println("데이터베이스 생성 또는 확인 완료");

                // 생성된 데이터베이스 선택
                stmt.executeUpdate("USE convenience_store");
                System.out.println("데이터베이스 선택 완료");

                // 스키마 실행
                try (InputStream is = getClass().getClassLoader().getResourceAsStream("database/schema.sql");
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        if (line.endsWith(";")) {
                            stmt.execute(sb.toString());
                            sb.setLength(0);
                        }
                    }
                    System.out.println("데이터베이스 스키마 초기화 성공");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("데이터베이스 초기화 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("데이터베이스 연결 종료 중 오류 발생: " + e.getMessage());
            }
        }
    }
}
