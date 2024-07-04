package model.dto;

public class InventoryLog {
    // 멤버변수
    private int logId;
    private int gameDate;
    private int productId;
    private int quantity;
    private String description;

    // 생성자
    public InventoryLog() {
    }

    // 풀 생성자
    public InventoryLog(int logId, int gameDate, int productId, int quantity, String description) {
        this.logId = logId;
        this.gameDate = gameDate;
        this.productId = productId;
        this.quantity = quantity;
        this.description = description;
    }

    // 게터 세터
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getGameDate() {
        return gameDate;
    }

    public void setGameDate(int gameDate) {
        this.gameDate = gameDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
