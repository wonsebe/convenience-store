// ConsoleView.java (View)
// 콘솔을 통해 사용자와 상호작용
// 사용자에게 메시지를 출력하는 메서드
// 사용자로부터 입력을 받는 메서드

package view;

import java.util.Scanner;

public class ConsoleView {
    private static ConsoleView instance;
    private Scanner scanner;

    private ConsoleView() {
        scanner = new Scanner(System.in);
    }

    // 싱글톤 패턴 전역접근 가능한 getInstance 메서드 (인스턴스 반환)
    public static ConsoleView getInstance() {
        if (instance == null) {
            instance = new ConsoleView();
        }
        return instance;
    }

    // 메시지 출력 메서드
    public void displayMessage(String message) {
        System.out.println(message);
    }

    // 사용자로부터 문자열 입력 받는 메서드
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // 사용자로부터 정수를 입력 받는 메서드
    public int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("유효한 숫자를 입력해주세요.");
            }
        }
    }

    // 사용자로부터 실수를 입력 받는 메서드
    public double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("유효한 숫자를 입력해주세요.");
            }
        }
    }

    // 사용자로부터 예/아니오 입력 받아 불리언값 반환하는 메서드
    public boolean getYesNoInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt + " (y/n): ").toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("'y' 또는 'n'을 입력해주세요.");
        }
    }

    // Scanner 리소스 해제 메서드
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}