package model.dto;

// 재고 로그 정보를 나타내는 데이터 전송 객체(DTO) 클래스
// 이 클래스는 로그 ID, 게임 날짜, 상품 ID, 수량, 설명을 포함
public class InventoryLog {
    // 멤버변수
    private int logId;          // 로그의 고유 식별자
    private int gameDate;       // 게임 턴 수
    private int productId;      // 관련 상품의 ID
    private int quantity;       // 기록 수량
    private String description; // 로그 설명

    // 기본 생성자, 모든 필드를 기본값으로 초기화
    public InventoryLog() {
    }

    // 모든 필드를 초기화하는 생성자
    public InventoryLog(int logId, int gameDate, int productId, int quantity, String description) {
        this.logId = logId;             // 로그의 고유 식별자
        this.gameDate = gameDate;       // 게임 내 날짜 (턴수)
        this.productId = productId;     // 관련 상품의 ID
        this.quantity = quantity;       // 재고 변동 수량
        this.description = description; // 로그 설명
    }

    // Getter와 Setter 메서드들
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

    // 객체의 문자열 표현 반환
    @Override
    public String toString() {
        return "InventoryLog{" +
                "logId=" + logId +
                ", gameDate=" + gameDate +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
} // InventoryLog 클래스 end
