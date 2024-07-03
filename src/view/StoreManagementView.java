// StoreManagementView.java (View)
// 가게 관리와 관련된 기능들을 메뉴로
// 사용자가 선택한 메뉴에 따라 적절한 컨트롤러 메서드 호출

package view;

import java.util.Scanner;

public class StoreManagementView {
    private static StoreManagementView instance;
    private Scanner scanner;

    private StoreManagementView() {
        scanner = new Scanner(System.in);
    }

    // 싱글톤 패턴 전역접근 가능한 getInstance 메서드 (인스턴스 반환)
    public static StoreManagementView getInstance() {
        if (instance == null) {
            instance = new StoreManagementView();
        }
        return instance;
    }

    public void displayStoreManagementMenu() {
        System.out.println("\n=== 편의점 관리 메뉴 ===");
        System.out.println("1. 상품 관리");
        System.out.println("2. 직원 관리");
        System.out.println("3. 매출 보고서");
        System.out.println("4. 게임 종료");
        System.out.print("선택: ");
    }

    public int getManagementChoice() {
        return Integer.parseInt(scanner.nextLine());
    }
}