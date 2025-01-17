package model.dao;

import model.dto.BoardDto;
import model.dto.GameStateDto;
import model.dto.InventoryLog;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameSaveDao {
    private static final GameSaveDao gameSaveDao = new GameSaveDao();

    private GameSaveDao() {
    }

    public static GameSaveDao getInstance() {
        return gameSaveDao;
    }

    public boolean saveGame(String loginId, GameStateDto gameState) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            // 트랜잭션 시작
            // 여러 SQL 명령어를 하나의 트랜잭션으로 처리하기 위해 자동 커밋을 비활성화
            conn.setAutoCommit(false);

            // 게임 턴 업데이트
            String updateTurnSql = "UPDATE store SET current_turn = ? WHERE login_id = ?";
            ps = conn.prepareStatement(updateTurnSql);
            ps.setInt(1, gameState.getCurrentTurn()); // 현재 턴 설정
            ps.setString(2, loginId); // 로그인 ID 설정
            ps.executeUpdate();

            // 재고 로그 저장
            String saveInventorySql = "INSERT INTO inventory_log (game_date, product_id, quantity, description, sale_price, store_id) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(saveInventorySql);
            // 재고 로그를 배치로 추가
            for (InventoryLog log : gameState.getInventoryLogs()) {
                ps.setInt(1, log.getGameDate()); // 게임 날짜 설정
                ps.setInt(2, log.getProductId()); // 제품 ID 설정
                ps.setInt(3, log.getQuantity()); // 수량 설정
                ps.setString(4, log.getDescription()); // 설명 설정
                ps.setInt(5, log.getSalePrice()); // 판매 가격 설정
                ps.setInt(6, log.getStoreId()); // 상점 ID 설정
                ps.addBatch();
            }
            ps.executeBatch();

            // 편의점 잔고 업데이트
            String updateBalanceSql = "UPDATE store SET balance = ? WHERE login_id = ?";
            ps = conn.prepareStatement(updateBalanceSql);
            ps.setInt(1, gameState.getStoreBalance()); // 편의점 잔고 설정
            ps.setString(2, loginId); // 로그인 ID 설정
            ps.executeUpdate();

            // 공지사항 저장
            String saveBoardSql = "INSERT INTO board (bcontent, bdate, store_id) VALUES (?, ?, (SELECT id FROM store WHERE login_id = ?))";
            ps = conn.prepareStatement(saveBoardSql);
            // 공지사항을 배치로 추가
            for (BoardDto board : gameState.getBoardNotices()) {
                ps.setString(1, board.getBcontent()); // 공지사항 내용 설정
                ps.setString(2, board.getBdate()); // 공지사항 날짜 설정
                ps.setString(3, loginId); // 로그인 ID 설정
                ps.addBatch();
            }
            ps.executeBatch();

            conn.commit();  // 트랜잭션 커밋
            return true;
        } catch (SQLException e) {
            System.out.println("게임저장 오류: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();  // 오류 발생 시 롤백
                } catch (SQLException ex) {
                    System.out.println("롤백 트랜잭션 오류: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }

    public GameStateDto loadGame(String loginId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        GameStateDto gameState = new GameStateDto();
        try {
            conn = DbUtil.getConnection();

            // 현재 턴 로드
            String loadTurnSql = "SELECT current_turn FROM store WHERE login_id = ?";
            ps = conn.prepareStatement(loadTurnSql);
            ps.setString(1, loginId);
            rs = ps.executeQuery();
            if (rs.next()) {
                gameState.setCurrentTurn(rs.getInt("current_turn"));
            }

            // 재고 로그 로드
            String loadInventorySql = "SELECT * FROM inventory_log WHERE game_date = ? AND store_id = (SELECT id FROM store WHERE login_id = ?)";
            ps = conn.prepareStatement(loadInventorySql);
            ps.setInt(1, gameState.getCurrentTurn());
            ps.setString(2, loginId);
            rs = ps.executeQuery();
            List<InventoryLog> inventoryLogs = new ArrayList<>();
            while (rs.next()) {
                InventoryLog log = new InventoryLog(
                        rs.getInt("log_id"),
                        rs.getInt("game_date"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getString("description"),
                        rs.getInt("sale_price"),
                        rs.getInt("store_id")
                );
                inventoryLogs.add(log);
            }
            gameState.setInventoryLogs(inventoryLogs);

            // 편의점 잔고 로드
            String loadBalanceSql = "SELECT balance FROM store WHERE login_id = ?";
            ps = conn.prepareStatement(loadBalanceSql);
            ps.setString(1, loginId);
            rs = ps.executeQuery();
            if (rs.next()) {
                gameState.setStoreBalance(rs.getInt("balance"));
            }

            // 공지사항 로드
            String loadBoardSql = "SELECT b.bmo, b.bcontent, b.bdate, s.id AS store_id, s.login_id " +
                    "FROM board b " +
                    "JOIN store s ON b.store_id = s.id " +
                    "WHERE s.login_id = ?";
            ps = conn.prepareStatement(loadBoardSql);
            ps.setString(1, loginId);
            rs = ps.executeQuery();
            List<BoardDto> boardNotices = new ArrayList<>();
            while (rs.next()) {
                BoardDto board = new BoardDto(
                        rs.getInt("bmo"),
                        rs.getString("bcontent"),
                        rs.getString("bdate"),
                        rs.getInt("store_id"),
                        rs.getString("login_id")
                );
                boardNotices.add(board);
            }
            gameState.setBoardNotices(boardNotices);

            return gameState;
        } catch (SQLException e) {
            System.out.println("게임로딩 오류: " + e.getMessage());
            return null;
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(ps);
            DbUtil.closeConnection(conn);
        }
    }
}
