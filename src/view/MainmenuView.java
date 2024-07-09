package view;

import controller.LoginController;
import controller.SignupController;
import util.ColorUtil;

import java.util.Scanner;

// 메인 메뉴와 관련된 화면을 처리하는 클래스
public class MainmenuView {
    // 싱글턴 인스턴스 생성
    private static final MainmenuView mmView = new MainmenuView();

    // Scanner 객체 생성
    private final Scanner scan = new Scanner(System.in);

    // 생성자
    private MainmenuView() {
    }

    // 싱글턴 메서드 반환
    public static MainmenuView getInstance() {
        return mmView;
    }

    // 메인메뉴 시작 메서드
    public void start() {
        System.out.print(
                ColorUtil.getColor("YELLOW") +
                        "         ____________________________\n" +
                        "       .'                  '----'  '.\n" +
                        "      . \"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\" .\n" +
                        "     .   .\"\"\"\"\"\".   .--\"\"\"\"\"\"\"\"\"\"-,   .\n" +
                        "    . \"\"\".       \"\"'  .--\"\"\"\"\"--.. \"\"\" .\n" +
                        "   .\"\"\"\"\"'-\"\"\"\"\"-  .-'   |\"|\"|   .'\"\"\"\"\".\n" +
                        "  .   .''.'.     .'      |\"|\"|    .      .\n" +
                        "  '._( ()   \\\"\"\".  _     _\"\"\"  _   .____.'\n" +
                        "    |.'.  ()'   ' --------------------.|\n" +
                        "    ||  '--'\"\"\"\"\"'          |         ||\n" +
                        "    ||    '.------'     |\"\"|\"\"|\"\"|    ||\n" +
                        "    ||     |.-.-.||-----|--|--|--|----||\n" +
                        "    ||     || | |||     |__|_-\"-_|    ||\n" +
                        "    ||     ||_|_|||    .-\"-\" ()  '.   ||\n" +
                        "    || .--.| [-] ||   .' ()     () .  ||\n" +
                        "    |.'    '.    || .'\"\"\"\"\"\"\"\"\"\"\"\"\"'. ||\n" +
                        "    |: ()   |    ||--\\mga   .   .  /---|\n" +
                        "    /    () \\____||___\\___________/____|\n" +
                        "    '-------'\n" +
                        ColorUtil.getColor("RESET")
        );

        System.out.println(ColorUtil.getColor("CYAN") + "1:로그인 / 2:회원가입 / 3:게임종료" + ColorUtil.getColor("RESET"));
        System.out.print("번호선택 >> ");
        int choice = scan.nextInt();
        switch (choice) {
            case 1 -> {
                handleLogin();
            }
            case 2 -> {
                handleSignup();
            }
            case 3 -> {
                System.out.println(ColorUtil.getColor("YELLOW") + "게임을 종료합니다. 안녕히 가세요!" + ColorUtil.getColor("RESET"));
                System.exit(0);
            }
            default -> {
                System.out.println(ColorUtil.getColor("RED") + "잘못된 선택입니다. 다시 선택해주세요." + ColorUtil.getColor("RESET"));
            }
        }
    }

    private void handleLogin() {
        System.out.print("아이디: ");
        String id = scan.next();
        System.out.print("비밀번호: ");
        String password = scan.next();

        boolean loginSuccess = LoginController.getInstance().login(id, password);
        if (loginSuccess) {
            System.out.println(ColorUtil.getColor("GREEN") + "로그인 성공!" + ColorUtil.getColor("RESET"));
            ProductView.getInstance().index();
        } else {
            System.out.println(ColorUtil.getColor("RED") + "로그인 실패. 아이디 또는 비밀번호를 확인해주세요." + ColorUtil.getColor("RESET"));
        }
    }

    private void handleSignup() {
        System.out.print("새로운 아이디: ");
        String id = scan.next();
        System.out.print("새로운 비밀번호: ");
        String password = scan.next();

        boolean signupSuccess = SignupController.getInstance().signup(id, password);
        if (signupSuccess) {
            System.out.println(ColorUtil.getColor("GREEN") + "회원가입 성공! 로그인해주세요." + ColorUtil.getColor("RESET"));
        } else {
            System.out.println(ColorUtil.getColor("RED") + "회원가입 실패. 다시 시도해주세요." + ColorUtil.getColor("RESET"));
        }
    }
}