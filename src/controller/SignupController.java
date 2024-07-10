package controller;

import model.dao.SignupDao;
import model.dto.AccountDto;
import util.InputValidator;

public class SignupController {
    private static final SignupController signupCon = new SignupController();

    private SignupController() {
    }

    public static SignupController getInstance() {
        return signupCon;
    }

    public boolean signup(String loginId, String loginPwd) {
        if (!InputValidator.isValidInput(loginId, loginPwd)) {
            System.out.println("Invalid username or password.");
            return false;
        }

        AccountDto account = new AccountDto();
        account.setLoginId(loginId);
        account.setLoginPwd(loginPwd);

        boolean signupSuccess = SignupDao.getInstance().signup(account);
        if (signupSuccess) {
            // 회원가입 성공 시 초기 게임 상태 설정
            PcController.getInstance().initializeNewGame(loginId);
        }
        return signupSuccess;
    }
}