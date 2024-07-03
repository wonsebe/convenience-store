// Sales.java (DTO)
// 판매로그 데이터 전송 객체

package model.dto;

public class Sales {
    private int id;         // 판매 ID (기본키)
    private int customerId; // 판매된 상품 ID
    private int productId;  // 구매한 고객 ID
    private int quantity;   // 판매 수량
    private int saleTurn;   // 판매가 발생된 게임턴

    public Sales(int id, int customerId, int productId, int quantity, int saleTurn) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.saleTurn = saleTurn;
    }
}
