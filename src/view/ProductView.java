package view;

import controller.PcController;
import model.dto.InventoryLog;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductView {
    private static final ProductView pView = new ProductView();
    // 사용자 입력을 받기 위한 Scanner 객체 생성
    Scanner scan = new Scanner(System.in);
    // 현재 턴을 저장하는 변수 초기화
    int turn = 1;

    private ProductView() {
    }

    public static ProductView getInstance() {
        return pView;
    }

    //멤버변수 : 입력객체
    public void index() {
        PcController.getInstance().purchase(turn);
        // 무한 루프를 통해 게임 진행
        while (true) {
            // 모든 제품에 대해 재고 확인
            for (int i = 1; i <= 30; i++) {
                System.out.print(" 제품번호: " + i);
                int inventory = PcController.getInstance().checkInventory(i);  // 개별 제품의 재고 확인
                System.out.println("       재고 = " + inventory);
            } // for문 끝

            // 사용자에게 행동 선택을 요청
            System.out.print("마이 턴 : 1.재고 구매 2.재고 확인 3. 재고 수정 4. 재고 삭제  5. 턴넘기기 : ");
            int choice = scan.nextInt();

            if (choice == 1) {
                // 사용자가 재고 구매를 선택한 경우

            } else if (choice == 2) {
                // 사용자가 재고 확인을 선택한 경우
                for (int i = 1; i <= 30; i++) {
                    System.out.print(" 제품번호: " + i);
                    int inventory = PcController.getInstance().checkInventory(i);  // 개별 제품의 재고 확인
                    System.out.println("       재고 = " + inventory);
                }

            } else if (choice == 3) {

            } else if (choice == 4) {

                //pDelete();
            } else if (choice == 5) { // 다음 턴 선택
                // 다음턴 넘기면 진행되는 메서드들
                System.out.print("턴 넘김");
                ArrayList<InventoryLog> logs = PcController.getInstance().purchase(turn);  // 현재 턴을 인자로 하여 구매 메서드 호출
                if (logs.size() > 0) {
                    for (InventoryLog log : logs) {
                        System.out.printf("손님이 %s을 %d개 샀습니다.", log.getLogId(), log.getQuantity());
                    }

                } else {
                    System.out.println("방문한 손님이 없습니다.");
                }
                turn++;  // 턴 수 증가
            } else {
                // 잘못된 선택을 한 경우 경고 메시지 출력
                System.out.println("잘못된 선택입니다. 다시 시도해주세요.");

            } // if문 끝
        } // while 끝
    }
//재고 삭제 함수 :
//    public void pDelete(){
//        System.out.println("삭제 페이지"); int productId=scan.nextInt();
//
//        boolean result = PcController.getInstance().pDelete(productId);
//
//
//    }

}