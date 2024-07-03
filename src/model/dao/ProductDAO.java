// ProductDAO.java
// 상품 데이터를 데이터베이스에 저장, 조회, 수정, 삭제하는 작업 담당
// 상품 추가, 상품 조회, 모든 상품 목록 조회, 상품 수정, 상품 삭제

package model.dao;

public class ProductDAO {
    private static ProductDAO instance;

    private ProductDAO() {}

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }
}
