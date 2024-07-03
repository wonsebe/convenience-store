// Product.java (DTO)
// 상품 데이터 전송 객체

package model.dto;

public class Product {
    private int id;         // 상품 ID
    private String name;    // 상품 이름
    private double price;   // 상품 가격
    private int stock;              // 상품 재고

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
