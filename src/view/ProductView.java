package view;

import controller.PcController;
import model.dto.InventoryLog;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductView {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final ProductView pView = new ProductView();

    // 사용자 입력을 받기 위한 Scanner 객체
    Scanner scan = new Scanner(System.in);

    // 현재 게임의 턴 수를 저장하는 변수
    int turn = 1;

    // private 생성자로 외부에서의 인스턴스 생성을 방지
    private ProductView() {
    }

    // 싱글톤 인스턴스를 반환하는 메서드
    public static ProductView getInstance() {
        return pView;
    }

    // 게임의 메인 루프를 담당하는 메서드
    public void index() {
        while (true) {
            // 현재 모든 상품의 재고 상태를 출력
            displayInventory();

            // 사용자 행동 선택 메뉴 출력 및 입력 받기
            System.out.print("행동 선택 : 0.다음 턴 1.재고 구매 2.재고 확인 3.재고 수정 4.재고 삭제 5.게임 종료 : ");
            int choice = scan.nextInt();

            // 향상된 switch 문을 사용하여 사용자 선택에 따른 동작 수행
            switch (choice) {
                case 0 -> processTurn();  // 다음 턴 진행
                case 1 -> System.out.println("재고 구매 기능은 아직 구현되지 않았습니다.");  // 재고 구매 (미구현)
                case 2 -> displayInventory();  // 재고 확인
                case 3 -> System.out.println("재고 수정 기능은 아직 구현되지 않았습니다.");  // 재고 수정 (미구현)
                case 4 -> System.out.println("재고 삭제 기능은 아직 구현되지 않았습니다.");  // 재고 삭제 (미구현)
                case 5 -> {
                    System.out.println("게임을 종료합니다.");  // 게임 종료
                    return;  // 메서드 종료
                }
                default -> System.out.println("잘못된 선택입니다. 다시 선택해주세요.");  // 잘못된 입력 처리
            }
        }
    }

    // 현재 모든 상품의 재고 상태를 출력하는 메서드
    private void displayInventory() {
        for (int i = 1; i <= PcController.getInstance().getProductTypeCount(); i++) {
            System.out.print("상품번호: " + i);
            int inventory = PcController.getInstance().checkInventory(i);
            System.out.println("       재고 = " + inventory);
        }
    }

    // 다음 턴을 처리하는 메서드
    private void processTurn() {
        System.out.println(turn + "번째 턴을 진행합니다.");
        ArrayList<InventoryLog> logs = PcController.getInstance().purchase(turn);
        if (logs.isEmpty()) {
            System.out.println("이번 턴에는 손님이 없었습니다.");
        } else {
            // 각 거래에 대한 로그를 출력
            for (InventoryLog log : logs) {
                String productName = PcController.getInstance().getProductName(log.getProductId());
                if (log.getQuantity() > 0) {
                    System.out.printf("손님이 %s을(를) %d개 구매했습니다.%n", productName, log.getQuantity());
                } else {
                    System.out.printf("손님이 %s을(를) 사려고 했으나 재고가 없어 그냥 나갔습니다.%n", productName);
                }
            }
        }
        turn++;  // 턴 수 증가
    }
}