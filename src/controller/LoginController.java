package controller;

import model.dao.LoginDao;
import model.dao.StoreDao;
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
            int storeId = StoreDao.getInstance().getStoreIdByLoginId(loginId);
            if (storeId == 0) {
                System.out.println("오류: 유효하지 않은 로그인 ID입니다.");
                return false;
            }
            PcController pcController = PcController.getInstance();
            pcController.setCurrentLoginId(loginId);
            pcController.setCurrentStoreId(storeId);
            pcController.updateStoreBalance(account.getBalance());
            pcController.initializeGameAfterLogin(loginId);
            // System.out.println("로그인 성공!");
            // System.out.println("현재 잔고: " + account.getBalance() + "원");
            return true;
        }
        return false;
    }
}