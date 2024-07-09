package view;

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
            case 1 -> { // 게임 로그인
                // 아이디 입력
                System.out.print("아이디: ");
                String id = scan.next();

                // 암호 입력
                System.out.print("비밀번호: ");
                String password = scan.next();

                // TODO: Controller로 아이디와 암호 전송

                // TODO: Controller 처리 결과에 따른 로직 구현
                ProductView.getInstance().index();

            }
            case 2 -> { // 게임 회원가입 (계정생성)
                // TODO: 회원가입 로직 구현
            }
            case 3 -> { // 게임종료
                System.out.println(ColorUtil.getColor("YELLOW") + "게임을 종료합니다. 안녕히 가세요!" + ColorUtil.getColor("RESET"));
                System.exit(0);
            }
            default ->
                    System.out.println(ColorUtil.getColor("RED") + "잘못된 선택입니다. 다시 선택해주세요." + ColorUtil.getColor("RESET"));
        }
    }
}