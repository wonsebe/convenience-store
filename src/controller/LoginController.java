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
        AccountDto account = new AccountDto(loginId, loginPwd);
        if (LoginDao.getInstance().login(account)) {
            PcController.getInstance().setCurrentLoginId(loginId);
            PcController.getInstance().setCurrentStoreId(account.getId());
            PcController.getInstance().updateStoreBalance(account.getBalance());
            PcController.getInstance().initializeGameAfterLogin(loginId);
            return true;
        }
        return false;
    }
}