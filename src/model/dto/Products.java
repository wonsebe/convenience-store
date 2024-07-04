package model.dto;

public class Products {
    // 멤버변수
    private int productId;
    private String name;
    private int price;
    private int expiryTurns;

    // 생성자
    public Products() {
    }

    // product_id를 제외한 생성자 추가
    public Products(String name, int price, int expiryTurns) {
        this.name = name;
        this.price = price;
        this.expiryTurns = expiryTurns;
    }

    // 풀 생성자 (기존과 동일)
    public Products(int productId, String name, int price, int expiryTurns) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.expiryTurns = expiryTurns;
    }

    // 게터 세터
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getExpiryTurns() {
        return expiryTurns;
    }

    public void setExpiryTurns(int expiryTurns) {
        this.expiryTurns = expiryTurns;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", expiryTurns=" + expiryTurns +
                '}';
    }
}
