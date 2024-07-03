// Customer.java (DTO)
// 고객 데이터 전송 객체

package model.dto;

public class Customer {
    // 멤버변수
    private int id;
    private int money;

    // 풀생성자
    public Customer(int id, int money) {
        this.id = id;
        this.money = money;
    }

    // 게터세터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
