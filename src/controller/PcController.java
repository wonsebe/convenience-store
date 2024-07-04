package controller;

import model.dao.InventoryDao;
import model.dao.ProductDao;
import model.dto.InventoryLog;
import model.dto.Products;

import java.util.ArrayList;
import java.util.Random;

public class PcController {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final PcController pControl = new PcController();
    // 등록된 상품 종류의 수를 저장하는 변수
    private int productTypeCount;

    // 생성자 (private으로 외부에서 인스턴스 생성 방지)
    private PcController() {
        // 초기화 시 등록된 상품 종류의 수 조회
        this.productTypeCount = ProductDao.getInstance().getProductTypeCount();
    }

    // 싱글톤 인스턴스 반환 메서드
    public static PcController getInstance() {
        return pControl;
    }

    // 구매 처리 메서드
    public ArrayList<InventoryLog> purchase(int turn) {
        ArrayList<InventoryLog> logs = new ArrayList<>();
        // 랜덤으로 1~5명의 방문 고객 생성
        int customerCount = new Random().nextInt(5) + 1;

        for (int i = 0; i < customerCount; i++) {
            // 랜덤으로 상품 ID (1 ~ 등록된 상품 종류 수) 선택
            int productId = new Random().nextInt(productTypeCount) + 1;
            // 랜덤으로 구매 수량 (1~5) 결정
            int buyCount = new Random().nextInt(5) + 1;

            // 현재 재고 확인
            int productCount = InventoryDao.getInstance().checkInventory(productId);

            if (productCount >= buyCount) {
                // 재고가 충분한 경우 전체 수량 구매
                InventoryLog log = InventoryDao.getInstance().purchase(productId, buyCount, turn);
                if (log != null) {
                    logs.add(log);
                }
            } else if (productCount > 0) {
                // 재고가 부족한 경우 남은 수량만 구매
                InventoryLog log = InventoryDao.getInstance().purchase(productId, productCount, turn);
                if (log != null) {
                    logs.add(log);
                }
            } else {
                // 재고가 0인 경우 구매하지 않음
                InventoryLog log = new InventoryLog();
                log.setProductId(productId);
                log.setQuantity(0);
                log.setDescription("재고 부족으로 구매 실패");
                logs.add(log);
            }
        }
        return logs;
    } // 구매 처리 메서드 end

    // 재고 확인 메서드
    public int checkInventory(int productId) {
        return InventoryDao.getInstance().checkInventory(productId);
    } // 재고 확인 메서드 end

    // 상품 이름 조회 메서드
    public String getProductName(int productId) {
        return ProductDao.getInstance().getProductName(productId);
    } // 상품 이름 조회 메서드 end

    // 등록된 상품 종류 수 반환 메서드
    public int getProductTypeCount() {
        return this.productTypeCount;
    } // 등록된 상품 종류 수 반환 메서드 end

    // 등록된 상품 종류 수 갱신 메서드
    public void updateProductTypeCount() {
        this.productTypeCount = ProductDao.getInstance().getProductTypeCount();
    } // 등록된 상품 종류 수 갱신 메서드 end

    // 상품 추가 메서드
    public boolean addProduct(Products product) {
        boolean result = ProductDao.getInstance().add(product);
        if (result) {
            // 상품이 성공적으로 추가되면 상품 종류 수를 갱신
            updateProductTypeCount();
        }
        return result;
    } // 상품 추가 메서드 end

    // 상품 수정 메서드
    public boolean updateProduct(Products product) {
        return ProductDao.getInstance().pUpdate(product);
    } // 상품 수정 메서드 end

    // 재고 삭제 메서드
    public boolean pDelete(int productId) {
        return InventoryDao.getInstance().pdelete(productId);
    }
}