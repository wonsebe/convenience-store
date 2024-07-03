// Game.java (DTO)
// 게임의 상태와 진행 관리
// 게임 시작할 때 초기 설정 수행
// 게임 진행 중의 상태를 저장
// 저장된 게임 상태를 불러와서 이어서 플레이

package model;

import model.dto.Customer;
import model.dto.Employee;

import java.util.ArrayList;

public class Game {
    private static Game instance;
    private Store store;
    private ArrayList<Employee> employees;
    private ArrayList<Customer> customers;
    private int currentTurn;

    private Game() {
        this.store = Store.getInstance();
        this.employees = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.currentTurn = 0;
    }

    // 싱글톤 패턴 전역접근 가능한 getInstance 메서드 (인스턴스 반환)
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // 게임 초기화 (새 게임 시작 또는 재시작)
    public void initializeGame() {
        this.store = Store.getInstance();
        this.store.resetStore();  // Store 클래스에 resetStore 메서드가 있다고 가정
        this.employees = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.currentTurn = 0;

        // 추가적인 초기화 로직
        // 예: 초기 상품 생성, 초기 고객 생성 등
    }

    // 게임 상태 저장
    public void saveGame() {
        // 게임 상태 저장 로직
    }

    // 게임 상태 불러오기
    public void loadGame() {
        // 저장된 게임 상태 불러오기 로직
    }

    // 게임 진행 (턴 증가)
    public void nextTurn() {
        currentTurn++;
        // 각 턴에서 수행할 로직 추가
    }

    // 게터 세터
    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
}
