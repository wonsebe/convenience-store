package model.Dto;

public class 재고 {
    int 번호;
    String 수량;
    String 내용;
    String 날짜;
    int 제품번호;

    public 재고() {
    }

    public 재고(int 번호, String 수량, String 내용, String 날짜, int 제품번호) {
        this.번호 = 번호;
        this.수량 = 수량;
        this.내용 = 내용;
        this.날짜 = 날짜;
        this.제품번호 = 제품번호;
    }

    public int get번호() {
        return 번호;
    }

    public void set번호(int 번호) {
        this.번호 = 번호;
    }

    public String get수량() {
        return 수량;
    }

    public void set수량(String 수량) {
        this.수량 = 수량;
    }

    public String get내용() {
        return 내용;
    }

    public void set내용(String 내용) {
        this.내용 = 내용;
    }

    public String get날짜() {
        return 날짜;
    }

    public void set날짜(String 날짜) {
        this.날짜 = 날짜;
    }

    public int get제품번호() {
        return 제품번호;
    }

    public void set제품번호(int 제품번호) {
        this.제품번호 = 제품번호;
    }


}
