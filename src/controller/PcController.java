package controller;

import model.dao.InventoryDao;
import model.dto.InventoryLog;

import java.util.ArrayList;
import java.util.Random;

public class PcController {
    private static final PcController pControl = new PcController();

    private PcController() {
    }

    public static PcController getInstance() {
        return pControl;
    }

    // 1. 구매 메서드
    public ArrayList<InventoryLog> purchase(int turn) {
        ArrayList<InventoryLog> logs = new ArrayList<>();
        // 0. 턴 넘기고 손님이 몇명 방문하는가?
        int customerCount = new Random().nextInt(5) + 1; // 1~5명 사이

        // 1. 랜덤으로 구매할 제품 선택
        for (int i = 0; i < customerCount; i++) {
            int productId = new Random().nextInt(30) + 1; // 1~30 번 상품 중 랜덤
            int buyCount = new Random().nextInt(5) + 1; // 1~5개 랜덤 구매

            // 해당 상품 수량이 충분히 있는지 확인
            int productCount = InventoryDao.getInstance().checkInventory(productId);

            if (productCount >= buyCount) {
                // 만약 편의점에 있는 해당 물품 수량이 구매하려는 수량 이상이면 사려는 수량을 구매한다
                logs.add(InventoryDao.getInstance().purchase(productId, buyCount, turn));
            } else if (productCount == 0) {
                // 찾는 상품이 0개이면 구매하지 않고 그냥 간다
                return null;
            } else {
                // 1개 이상이지만 찾는 수량보다 적으면 재고 있는거만 구매한다.
                logs.add(InventoryDao.getInstance().purchase(productId, productCount, turn));
            } // if 끝
        } // for 끝
        return null;
    } // purchase 메서드 끝

    // 재고 확인 메서드
    public int checkInventory(int productId) {
        return InventoryDao.getInstance().checkInventory(productId);
    }

//    public boolean pDelete(int productId) {
//
//}
}


