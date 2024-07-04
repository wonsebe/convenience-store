package controller;

import model.dao.InventoryDao;
import model.dto.Products;

import java.util.ArrayList;
import java.util.Random;

public class PcController {
    private static PcController pControl=new PcController();
    private PcController(){}
    public static PcController getInstance(){return pControl;}

    // 1. 구매 메서드
    public boolean purchase(int turn) {
        // 1. 랜덤으로 구매할 제품 선택
        int productId = new Random().nextInt(30) + 1; // 1~30
        System.out.println("제품번호: " + productId + " 구매했습니다.");

        // 재고 확인
        int inventory = checkInventory(productId);
        System.out.println("재고: " + inventory);

        if (inventory <= 0) {
            System.out.println("재고 부족");
            return false;
        }

        // 2. 랜덤으로 구매 수량
        int quantity = new Random().nextInt(11); // 0 ~ 10 (선택한 제품의 재고 수량)
        System.out.println("구매수량: " + quantity + "입니다.");

        InventoryDao.getInstance().purchase(productId, quantity, turn);

        return true;
    }

    // 전체 출력
    public ArrayList<Products> pPrint(){
        return InventoryDao.getInstance().pPrint();
    }


    // 재고 확인 메서드
    public int checkInventory(int productId) {
        return InventoryDao.getInstance().checkInventory(productId);
    }

    public boolean pDelete(int productId) {
        return InventoryDao.getInstance().pdelete(productId);


    }

    public boolean add(Products products) {

        return InventoryDao.getInstance().add(products);

    }
    // 물품 수정
    public boolean pUpdate(Products products){
        return InventoryDao.getInstance().pUpdate(products);
    }


}


