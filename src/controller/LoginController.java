package controller;

import model.dao.LoginDao;
import model.dto.AccountDto;
import util.InputValidator;

public class LoginController {
    private static final LoginController loginCon = new LoginController();

    private LoginController() {
    }

    public static LoginController getInstance() {
        return loginCon;
    }

    public boolean login(String loginId, String loginPwd) {
        if (!InputValidator.isValidInput(loginId, loginPwd)) {
            System.out.println("Invalid username or password.");
            return false;
        }

        AccountDto account = new AccountDto();
        account.setLoginId(loginId);
        account.setLoginPwd(loginPwd);

        return LoginDao.getInstance().login(account);
    }
}