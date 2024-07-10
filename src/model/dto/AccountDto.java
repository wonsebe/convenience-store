package model.dto;

public class AccountDto {
    // 멤버변수
    private int id;
    private String loginId;
    private String loginPwd;

    // 빈 생성자
    public AccountDto() {

    }

    // 회원가입시 필요한 생성자
    public AccountDto(String loginId, String loginPwd) {
        this.loginId = loginId;
        this.loginPwd = loginPwd;
    }

    // 풀 생성자

    public AccountDto(int id, String loginId, String loginPwd) {
        this.id = id;
        this.loginId = loginId;
        this.loginPwd = loginPwd;
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
