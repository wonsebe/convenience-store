package view;

import controller.PcController;
import model.dto.InventoryLog;
import model.dto.Products;

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
            // displayInventory();

            // 사용자 행동 선택 메뉴 출력 및 입력 받기
            System.out.print("행동 선택 : 0.다음 턴 1.재고 구매 2.재고 확인 3.상품 추가 4.상품 수정 5.재고 삭제 6.게임 종료 : ");
            int choice = scan.nextInt();

            // 향상된 switch 문
            switch (choice) {
                case 0 -> processTurn();  // 다음 턴 진행
                case 1 -> System.out.println("재고 구매 기능은 아직 구현되지 않았습니다.");  // 재고 구매 (미구현)
                case 2 -> displayInventory();  // 재고 확인
                case 3 -> addProduct();  // 상품 추가
                case 4 -> updateProduct();  // 상품 수정
                case 5 -> pDelete();  // 재고 삭제
                case 6 -> {
                    System.out.println("게임을 종료합니다.");  // 게임 종료
                    return;  // 메서드 종료
                }
                default -> System.out.println("잘못된 선택입니다. 다시 선택해주세요.");  // 잘못된 입력 처리
            } // switch 끝
        } // while 끝
    } // 게임의 메인 루프를 담당하는 메서드 end

    // 현재 모든 상품의 재고 상태를 출력하는 메서드
    private void displayInventory() {
        for (int i = 1; i <= PcController.getInstance().getProductTypeCount(); i++) {
            System.out.print("상품번호: " + i);
            int inventory = PcController.getInstance().checkInventory(i);
            System.out.println("       재고 = " + inventory);
        }
    } // 현재 모든 상품의 재고 상태를 출력하는 메서드 end

    // 상품 수정 메서드
    public void updateProduct() {
        System.out.println("상품 수정 페이지");
        System.out.print("수정할 상품 ID: ");
        int productId = scan.nextInt();
        System.out.print("새로운 가격: ");
        int newPrice = scan.nextInt();

        Products updatedProduct = new Products();
        updatedProduct.setProductId(productId);
        updatedProduct.setPrice(newPrice);

        boolean result = PcController.getInstance().updateProduct(updatedProduct);

        if (result) {
            System.out.println("상품 수정 성공!");
        } else {
            System.out.println("상품 수정 실패");
        }
    } // 상품 수정 메서드 end

    // 상품 추가 메서드
    public void addProduct() {
        System.out.println("상품 추가 페이지");
        scan.nextLine(); // 버퍼 비우기
        System.out.print("상품명: ");
        String name = scan.nextLine();
        System.out.print("가격: ");
        int price = scan.nextInt();
        System.out.print("유통기한(턴): ");
        int expiryTurns = scan.nextInt();

        Products newProduct = new Products();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setExpiryTurns(expiryTurns);

        boolean result = PcController.getInstance().addProduct(newProduct);

        if (result) {
            System.out.println("상품 추가 성공!");
        } else {
            System.out.println("상품 추가 실패");
        }
    } // 상품 추가 메서드 end

    // 재고 삭제 함수
    public boolean pDelete() {
        // 삭제할 제품 번호를 입력받기
        System.out.println("삭제 페이지");
        System.out.print("삭제할 제품번호를 입력해주세요: ");
        int productId = scan.nextInt();

        // PcController에서 pDelete 함수 호출
        boolean result = PcController.getInstance().pDelete(productId);

        // 삭제 결과에 따른 메시지 출력
        if (result) {
            System.out.println("삭제 성공!");
            return true;
        } else {
            System.out.println("삭제 실패");
            return false;
        }
    } // 재고 삭제 함수 end

    // 다음 턴을 처리하는 메서드
    private void processTurn() {
        System.out.println(turn + "번째 턴을 진행합니다.");
        ArrayList<InventoryLog> logs = PcController.getInstance().purchase(turn);
        if (logs.isEmpty()) {
            System.out.println("이번 턴에는 손님이 없었습니다.");
        } else {
            for (InventoryLog log : logs) {
                String productName = PcController.getInstance().getProductName(log.getProductId());
                int currentInventory = PcController.getInstance().checkInventory(log.getProductId());
                if (log.getQuantity() != 0) {  // 구매 성공 시 quantity는 음수 값
                    System.out.printf("손님이 %s을(를) %d개 구매했습니다. (현재 재고: %d)%n",
                            productName, -log.getQuantity(), currentInventory);
                } else {
                    System.out.printf("손님이 %s을(를) 사려고 했으나 재고가 부족하여 구매하지 못했습니다. (현재 재고: %d)%n",
                            productName, currentInventory);
                }
            }
        }
        turn++; // 턴 증가
    } // 다음 턴을 처리하는 메서드 end
} // ProductView 클래스 종료