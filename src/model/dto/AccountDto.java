package model.dto;

public class AccountDto {
    // 멤버변수
    private int id; // 계정의 고유 식별자
    private String loginId; // 로그인 ID
    private String loginPwd; // 로그인 비밀번호
    private int balance; // 계정 잔고

    // 빈 생성자
    public AccountDto() {

    }

    // 회원가입시 필요한 생성자
    public AccountDto(String loginId, String loginPwd) {
        this.loginId = loginId; // 로그인 ID 초기화
        this.loginPwd = loginPwd; // 로그인 비밀번호 초기화
    }

    // 풀 생성자

    public AccountDto(int id, String loginId, String loginPwd) {
        this.id = id; // 계정 고유 식별자 초기화
        this.loginId = loginId; // 로그인 ID 초기화
        this.loginPwd = loginPwd; // 로그인 비밀번호 초기화
    }

    // 게터 세터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    // toString
    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                '}';
    }
}
