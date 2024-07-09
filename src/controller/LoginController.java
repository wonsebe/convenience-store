package controller;

public class LoginController {
    // 싱글턴 인스턴스 생성
    private static final LoginController loginCon = new LoginController();

    // 생성자
    private LoginController() {

    }

    // 싱글턴 메서드 반환
    public static LoginController getInstance() {
        return loginCon;
    }


}
