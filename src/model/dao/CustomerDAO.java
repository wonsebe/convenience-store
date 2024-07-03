// CustomerDAO.java
// 고객 데이터를 데이터베이스에 저장, 조회, 수정, 삭제하는 작업
// 고객 추가, 고객 조회, 고객 목록 조회, 고객 수정, 고객 삭제

package model.dao;

public class CustomerDAO {
    private static CustomerDAO instance;

    private CustomerDAO() {}

    public static CustomerDAO getInstance() {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        return instance;
    }
}
