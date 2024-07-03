// Employee.java (DTO)
// 직원 데이터 전송 객체

package model.dto;

public class Employee {
    // 멤버변수
    private int id;         // 직원 ID
    private String name;    // 직원 이름
    private double salary;  // 직원 급여

    // 풀생성자


    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // 게터세터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
