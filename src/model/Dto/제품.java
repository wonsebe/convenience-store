package model.Dto;

public class 제품 {
    int 번호;
    int 이름;
    int 가격;

    public 제품() {
    }

    public 제품(int 번호, int 이름, int 가격) {
        this.번호 = 번호;
        this.이름 = 이름;
        this.가격 = 가격;
    }

    public int get번호() {
        return 번호;
    }

    public void set번호(int 번호) {
        this.번호 = 번호;
    }

    public int get이름() {
        return 이름;
    }

    public void set이름(int 이름) {
        this.이름 = 이름;
    }

    public int get가격() {
        return 가격;
    }

    public void set가격(int 가격) {
        this.가격 = 가격;
    }
}
