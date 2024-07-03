// EmployeeDAO.java
// 직원 데이터를 데이터베이스에 저장, 조회, 수정, 삭제
// 직원 추가, 직원 조회, 직원 목록 조회, 직원 수정, 직원 삭제(해고)

package model.dao;

public class EmployeeDAO {
    private static EmployeeDAO instance;

    private EmployeeDAO() {}

    public static EmployeeDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDAO();
        }
        return instance;
    }
}
