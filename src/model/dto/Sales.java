// Sales.java (DTO)
// 판매로그 데이터 전송 객체

package model.dto;

public class Sales {
    // 멤버변수
    private int id;         // 판매 ID (기본키)
    private int customerId; // 판매된 상품 ID
    private int productId;  // 구매한 고객 ID
    private int quantity;   // 판매 수량
    private int saleTurn;   // 판매가 발생된 게임턴

    // 풀생성자
    public Sales(int id, int customerId, int productId, int quantity, int saleTurn) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.saleTurn = saleTurn;
    }

    // 게터 세터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public int getSaleTurn() {
        return saleTurn;
    }

    public void setSaleTurn(int saleTurn) {
        this.saleTurn = saleTurn;
    }
}
