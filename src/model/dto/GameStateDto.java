package model.dto;

import java.util.ArrayList;
import java.util.List;

public class GameStateDto {
    private int storeId; // store의 고유 식별자
    private String loginId; // 로그인 ID를 저장
    private int balance; // 현재 잔고
    private int currentTurn; // 현재 턴
    private List<InventoryLog> inventoryLogs; // 재고 로그 리스트
    private List<BoardDto> boardNotices; // 공지사항 리스트
    private List<Products> products; // 상품 리스트
    private int lastTurnTotalSales; // 마지막 턴의 총 매출액

    // 기본 생성자
    public GameStateDto() {
        this.inventoryLogs = new ArrayList<>(); // 재고 로그 리스트 초기화
        this.boardNotices = new ArrayList<>(); // 공지사항 리스트 초기화
    }

    // 모든 필드를 초기화하는 새로운 생성자
    public GameStateDto(int storeId, String loginId, int balance, int currentTurn, List<InventoryLog> inventoryLogs, List<BoardDto> boardNotices) {
        this.storeId = storeId; // store의 고유 식별자 설정
        this.loginId = loginId; // 로그인 ID 설정
        this.balance = balance; // 현재 잔고 설정
        this.currentTurn = currentTurn; // 현재 턴 설정
        this.inventoryLogs = inventoryLogs; // 재고 로그 리스트 설정
        this.boardNotices = boardNotices; // 공지사항 리스트 설정
    }

    // Getter와 Setter 메서드들
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
