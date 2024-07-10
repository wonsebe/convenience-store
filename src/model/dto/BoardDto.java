package model.dto;

public class BoardDto {
    private int bmo; // 게시물 번호
    private String bcontent; // 게시물 내용
    private String bdate; // 게시물 작성 날짜
    private int store_id; // store의 고유 식별자 (작성자)
    private String authorLoginId; // 작성자의 로그인 ID

    public BoardDto(int bmo, String bcontent, String bdate, int store_id, String authorLoginId) {
        this.bmo = bmo; // 게시물 번호 설정
        this.bcontent = bcontent; // 게시물 내용 설정
        this.bdate = bdate; // 게시물 작성 날짜 설정
        this.store_id = store_id; // store의 고유 식별자 설정
        this.authorLoginId = authorLoginId; // 작성자의 로그인 ID 설정
    }

    // Getter와 Setter 메서드들
    public int getBmo() {
        return bmo;
    }

    public void setBmo(int bmo) {
        this.bmo = bmo;
    }

    public String getBcontent() {
        return bcontent;
    }

    public void setBcontent(String bcontent) {
        this.bcontent = bcontent;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getAuthorLoginId() {
        return authorLoginId;
    }

    public void setAuthorLoginId(String authorLoginId) {
        this.authorLoginId = authorLoginId;
    }
}
