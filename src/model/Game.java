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
    private Store store;
    private ArrayList<Employee> employees;
    private ArrayList<Customer> customers;
    private int currentTurn;

    public Game(Store store, ArrayList<Employee> employees, ArrayList<Customer> customers) {
        this.store = store;
        this.employees = employees;
        this.customers = customers;
        this.currentTurn = 0; // 게임 시작 시 0번째 턴
    }

    // 게임 초기화
    public void initializeGame() {
        // 초기 설정 수행
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

    // Getters and Setters

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
