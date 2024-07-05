package model.dto;

// 상품 정보를 나타내는 데이터 전송 객체(DTO) 클래스
public class Products {
    // 멤버변수
    private int productId;      // 상품의 고유 식별자
    private String name;        // 상품의 이름
    private int price;          // 상품의 가격
    private int expiryTurns;    // 상품의 유통기한 (턴 단위)
    private int stock;          // 재고

    // 기본 생성자, 모든 필드를 기본값으로 초기화
    public Products() {
    }

    // productId를 제외한 필드를 초기화하는 생성자
    // 새로운 상품을 생성할 때 사용
    public Products(String name, int price,  int stock , int expiryTurns ) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.expiryTurns = expiryTurns;
    }

    // 모든 필드를 초기화하는 생성자
    // 데이터베이스에서 상품 정보를 로드할 때 사용
    public Products(int productId, String name, int price, int stock , int expiryTurns) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.expiryTurns = expiryTurns;

    }

    // Getter와 Setter 메서드들
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

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

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
                ", stock=" + stock +
                '}';
    }

} // Products 클래스 end
