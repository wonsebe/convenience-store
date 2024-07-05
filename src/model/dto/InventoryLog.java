package model.dto;

public class InventoryLog {
    // 멤버변수
    private int logId; //강도함수에 넣을 기본키
    private int gameDate; //강도 함수에 넣을 몇번째 턴수에 털어갔냐 기록
    private int productId;
    private int quantity; //강도함수에 넣을 수량 기록
    private String description; //강도함수에 넣을 내용 기록

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
