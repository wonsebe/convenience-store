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

        return SignupDao.getInstance().signup(account);
    }
}