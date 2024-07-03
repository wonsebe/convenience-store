// Store.java (DTO)
// 가게의 재고, 매출, 직원 등 가게 운영과 관련된 모든 데이터 관리

package model;

import model.dto.Product;

import java.util.ArrayList;

public class Store {
    private ArrayList<Product> inventory;
    private double revenue;

    public Store() {
        this.inventory = new ArrayList<>();
        this.revenue = 0.0; // 초기 매출 0
    }

    // 재고 추가
    public void addProduct(Product product) {
        inventory.add(product);
    }

    // 재고 삭제
    public void removeProduct(Product product) {
        inventory.remove(product);
    }

    // 재고 검색
    public Product findProductById(int productId) {
        for (Product product : inventory) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null; // 상품을 찾지 못한 경우
    }

    // 매출 증가
    public void addRevenue(double amount) {
        revenue += amount;
    }

    // 게터 세터
    public ArrayList<Product> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Product> inventory) {
        this.inventory = inventory;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
