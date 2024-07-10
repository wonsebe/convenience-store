package model.dto;

import java.util.ArrayList;
import java.util.List;

public class GameStateDto {
    private int storeId;
    private String loginId;  // 로그인 ID를 저장하기 위한 새 필드
    private int balance;
    private int currentTurn;
    private List<InventoryLog> inventoryLogs;
    private List<BoardDto> boardNotices;
    private List<Products> products;
    private int lastTurnTotalSales;

    public GameStateDto() {
        this.inventoryLogs = new ArrayList<>();
        this.boardNotices = new ArrayList<>();
    }

    // 새로운 생성자 추가
    public GameStateDto(int storeId, String loginId, int balance, int currentTurn, List<InventoryLog> inventoryLogs, List<BoardDto> boardNotices) {
        this.storeId = storeId;
        this.loginId = loginId;
        this.balance = balance;
        this.currentTurn = currentTurn;
        this.inventoryLogs = inventoryLogs;
        this.boardNotices = boardNotices;
    }

    // Getter and Setter methods
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public List<InventoryLog> getInventoryLogs() {
        return inventoryLogs;
    }

    public void setInventoryLogs(List<InventoryLog> inventoryLogs) {
        this.inventoryLogs = inventoryLogs;
    }

    public List<BoardDto> getBoardNotices() {
        return boardNotices;
    }

    public void setBoardNotices(List<BoardDto> boardNotices) {
        this.boardNotices = boardNotices;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public int getLastTurnTotalSales() {
        return lastTurnTotalSales;
    }

    public void setLastTurnTotalSales(int lastTurnTotalSales) {
        this.lastTurnTotalSales = lastTurnTotalSales;
    }

    public int getStoreBalance() {
        return balance;
    }

    public void setStoreBalance(int balance) {
        this.balance = balance;
    }
}
