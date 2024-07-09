package controller;

public class SignupController {
    // 싱글턴 객체
    private static final SignupController signupCon = new SignupController();

    // 생성자
    private SignupController() {

    }

    // 싱글턴 메서드 반환
    public SignupController getInstance() {
        return signupCon;
    }


}
