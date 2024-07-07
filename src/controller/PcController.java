package controller;

import model.dao.InventoryDao;
import model.dao.ProductDao;
import model.dto.InventoryLog;
import model.dto.Products;

import java.util.ArrayList;
import java.util.Random;

// 편의점 시뮬레이션 게임의 핵심 로직을 관리하는 컨트롤러 클래스
// 싱글톤 패턴을 사용해 구현됨
public class PcController {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final PcController pControl = new PcController();
    // 등록된 상품 종류의 수를 저장하는 변수
    private int productTypeCount;

    // private 생성자. 외부에서 인스턴스 생성 방지
    // 초기화 시 등록된 상품 종류의 수 조회
    private PcController() {
        // 초기화 시 등록된 상품 종류의 수 조회
        this.productTypeCount = ProductDao.getInstance().getProductTypeCount();
    }

    // 싱글톤 인스턴스 반환 메서드
    public static PcController getInstance() {
        return pControl;
    }

    // 구매 처리를 수행하는 메서드
    // 랜덤한 수의 고객이 랜덤한 상품을 랜덤 수량으로 구매하려 시도
    public ArrayList<InventoryLog> purchase(int turn) {
        ArrayList<InventoryLog> logs = new ArrayList<>();
        // 3~15명의 랜덤한 고객 수 생성
        int customerCount = new Random().nextInt(13) + 3;

        for (int i = 0; i < customerCount; i++) {
            // 랜덤한 상품 ID 선택
            int productId = new Random().nextInt(productTypeCount) + 1;
            // 1~5개의 랜덤한 구매 수량 생성
            int buyCount = new Random().nextInt(5) + 1;

            int productCount = InventoryDao.getInstance().checkInventory(productId);
            // 확인용 콘솔
            // System.out.println("상품 ID " + productId + " 구매 시도: 요청 수량 " + buyCount + ", 현재 재고 " + productCount); // 디버깅을 위한 출력

            if (productCount >= buyCount) {
                InventoryLog log = InventoryDao.getInstance().purchase(productId, buyCount, turn);
                if (log != null) {
                    logs.add(log);
                    // 확인용 콘솔
                    // System.out.println("구매 성공: 상품 ID " + productId + ", 수량 " + buyCount); // 디버깅을 위한 출력
                }
            } else {
                InventoryLog log = new InventoryLog();
                log.setProductId(productId);
                log.setQuantity(0);
                log.setDescription("재고 부족으로 구매 실패");
                logs.add(log);
                // 확인용 콘솔
                // System.out.println("구매 실패: 상품 ID " + productId + ", 요청 수량 " + buyCount + ", 현재 재고 " + productCount); // 디버깅을 위한 출력
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

    // 상품 추가 메서드 수정
    public boolean addProduct(Products product) {
        boolean result = ProductDao.getInstance().add(product);
        if (result) {
            // 상품이 성공적으로 추가되면 상품 종류 수를 갱신
            updateProductTypeCount();
        }
        return result; // 상품 추가 성공여부 반환
    } // 상품 추가 메서드 end

    // 상품 수정 메서드
    public boolean updateProduct(Products product) {
        return ProductDao.getInstance().pUpdate(product);
    } // 상품 수정 메서드 end

    // 재고 삭제 메서드
    public boolean pDelete(int productId) {
        return InventoryDao.getInstance().pdelete(productId);
    }

    // 전체 출력
    public ArrayList<Products> pPrint() {
        return InventoryDao.getInstance().pPrint();
    }
} // PcController 클래스 end