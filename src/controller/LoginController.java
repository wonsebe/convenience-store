package controller;

import model.dao.LoginDao;
import model.dto.AccountDto;

public class LoginController {
    private static final LoginController loginCon = new LoginController();

    private LoginController() {
    }

    public static LoginController getInstance() {
        return loginCon;
    }

    public boolean login(String loginId, String loginPwd) {
        if (LoginDao.getInstance().login(new AccountDto(loginId, loginPwd))) {
            PcController.getInstance().setCurrentLoginId(loginId);
            return true;
        }
        return false;
    }
}