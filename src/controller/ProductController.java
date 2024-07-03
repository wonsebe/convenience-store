// ProductController.java
// 상품의 CRUD(생성, 읽기, 업데이트, 삭제) 작업 처리
// 새로운 상품을 추가하거나, 기존 상품을 수정, 삭제하는 기능
// 상품 재고를 관리하고, 상품 리스트를 사용자에게 보여줌
// addProduct(), updateProduct() 등

package controller;

public class ProductController {
    private static ProductController instance;

    private ProductController() {}

    // 싱글톤 패턴 전역접근 가능한 getInstance 메서드 (인스턴스 반환)
    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }

}
