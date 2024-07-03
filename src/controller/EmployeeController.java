// EmployeeController.java
// 직원의 CRUD 작업 처리
// 새로운 직원을 고용하거나, 기존 직원을 해고, 직원 정보를 수정하는 기능
// 직원 근무 시간 관리, 급여 계산 등
// hireEmployee(), fireEmployee(), updateEmployee() 등

package controller;

public class EmployeeController {
    private static EmployeeController instance;

    private EmployeeController() {}

    // 싱글톤 패턴 전역접근 가능한 getInstance 메서드 (인스턴스 반환)
    public static EmployeeController getInstance() {
        if (instance == null) {
            instance = new EmployeeController();
        }
        return instance;
    }
}
