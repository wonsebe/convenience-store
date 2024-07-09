package model.dao;

public class SignupDao {
    // 싱글턴 인스턴스 생성
    private static final SignupDao signupDao = new SignupDao();

    // 생성자
    private SignupDao() {

    }

    // 싱글턴 메서드 반환
    public SignupDao getInstance() {
        return signupDao;
    }


}
