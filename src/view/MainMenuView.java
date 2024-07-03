// MainMenuView.java (View)
// 게임의 메인 메뉴 표시
// 사용자로부터 선택을 받아서 해당 기능으로 이동

package view;

import java.util.Scanner;

public class MainMenuView {
    // 싱글톤 인스턴스를 저장할 정적 변수
    private static MainMenuView instance;
    // 사용자 입력을 받기 위한 Scanner 객체
    private Scanner scanner;

    // 외부에서 직접 객체 생성을 방지하는 private 생성자
    private MainMenuView() {
        // Scanner 객체 초기화. System.in은 표준 입력(키보드)
        scanner = new Scanner(System.in);
    }

    // 싱글톤 인스턴스를 반환하는 정적 메서드
    // 이 메서드를 통해서만 MainMenuView 객체에 접근할 수 있음
    public static MainMenuView getInstance() {
        // 인스턴스가 아직 생성되지 않았다면 새로 생성
        if (instance == null) {
            instance = new MainMenuView();
        }
        // 생성된 인스턴스를 반환.
        return instance;
    }

    // 메인 메뉴를 콘솔에 표시하는 메서드
    public void displayMainMenu() {
        System.out.println("=== 편의점 육성 시뮬레이션 게임 ===");
        System.out.println("1. 게임 시작");
        System.out.println("2. 게임 불러오기");
        System.out.println("3. 옵션 설정");
        System.out.println("4. 게임 종료");
        System.out.print("선택: ");
    }

    // 사용자의 메뉴 선택을 받아 처리하는 메서드
    public int getMenuChoice() {
        int choice = 0;
        boolean validInput = false;

        // 사용자가 유효한 입력을 할 때까지 반복한다
        while (!validInput) {
            try {
                // 사용자의 입력을 정수로 변환한다
                choice = Integer.parseInt(scanner.nextLine());

                // 입력값이 1에서 4 사이인지 확인한다
                if (choice >= 1 && choice <= 4) {
                    validInput = true; // 유효한 입력이면 루프를 종료한다
                } else {
                    // 범위를 벗어난 입력일 경우 에러 메시지를 출력하고 다시 입력바등
                    System.out.println("잘못된 선택입니다. 1에서 4 사이의 숫자를 입력해주세요.");
                    System.out.print("다시 선택: ");
                }
            } catch (NumberFormatException e) {
                // 숫자가 아닌 입력일 경우 예외가 발생하며, 다시 처리함
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                System.out.print("다시 선택: ");
            }
        }

        // 최종적으로 선택된 메뉴 번호 반환
        return choice;
    }
}