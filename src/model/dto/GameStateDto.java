package model.dto;

import java.util.List;

public class GameStateDto {
    private int currentTurn;
    private List<InventoryLog> inventoryLogs;
    private int storeBalance;
    private List<BoardDto> boardNotices;
    private int lastTurnTotalSales;
    private List<Products> products;
    private int productTypeCount;

    public GameStateDto() {
    }

    public GameStateDto(int currentTurn, List<InventoryLog> inventoryLogs, int storeBalance, List<BoardDto> boardNotices) {
        this.currentTurn = currentTurn;
        this.inventoryLogs = inventoryLogs;
        this.storeBalance = storeBalance;
        this.boardNotices = boardNotices;
    }

    public GameStateDto(int currentTurn, List<InventoryLog> inventoryLogs, int storeBalance, List<BoardDto> boardNotices, int lastTurnTotalSales) {
        this.currentTurn = currentTurn;
        this.inventoryLogs = inventoryLogs;
        this.storeBalance = storeBalance;
        this.boardNotices = boardNotices;
        this.lastTurnTotalSales = lastTurnTotalSales;
    }

    public GameStateDto(int currentTurn, List<InventoryLog> inventoryLogs, int storeBalance,
                        List<BoardDto> boardNotices, int lastTurnTotalSales, List<Products> products) {
        this.currentTurn = currentTurn;
        this.inventoryLogs = inventoryLogs;
        this.storeBalance = storeBalance;
        this.boardNotices = boardNotices;
        this.lastTurnTotalSales = lastTurnTotalSales;
        this.products = products;
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

    public int getStoreBalance() {
        return storeBalance;
    }

    public void setStoreBalance(int storeBalance) {
        this.storeBalance = storeBalance;
    }

    public List<BoardDto> getBoardNotices() {
        return boardNotices;
    }

    public void setBoardNotices(List<BoardDto> boardNotices) {
        this.boardNotices = boardNotices;
    }

    public int getLastTurnTotalSales() {
        return lastTurnTotalSales;
    }

    public void setLastTurnTotalSales(int lastTurnTotalSales) {
        this.lastTurnTotalSales = lastTurnTotalSales;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public int getProductTypeCount() {
        return products != null ? products.size() : 0;
    }

    public void setProductTypeCount(int productTypeCount) {
        this.productTypeCount = productTypeCount;
    }

}
