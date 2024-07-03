// Store.java (DTO)
// 가게의 재고, 매출, 직원 등 가게 운영과 관련된 모든 데이터 관리

package model;

import model.dto.Product;

import java.util.ArrayList;

public class Store {
    private static Store instance;
    private ArrayList<Product> inventory;
    private double revenue;

    private Store() {
        this.inventory = new ArrayList<>();
        this.revenue = 0.0;
    }

    // 싱글톤 패턴 전역접근 가능한 getInstance 메서드 (인스턴스 반환)
    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
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

    // 스토어 리셋 메서드
    public void resetStore() {
        // Store의 모든 inventory(상품) 제거
        this.inventory.clear();
        this.revenue = 0.0;
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
