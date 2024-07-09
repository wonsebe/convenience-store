package model.dao;

public class LoginDao {
    // 싱글턴 객체
    private static final LoginDao loginDao = new LoginDao();

    // 생성자
    private LoginDao() {

    }

    // 싱글턴 메서드 반환
    public LoginDao getInstance() {
        return loginDao;
    }

}
