// SalesDAO.java
// 판매 데이터를 데이터베이스에 저장, 조회, 수정, 삭제하는 작업
// 판매 기록 추가, 판매 기록 조회, 판매기록 목록조회, 판매 기록 수정, 판매 기록 삭제

package model.dao;

public class SalesDAO {
    private static SalesDAO instance;

    private SalesDAO() {}

    public static SalesDAO getInstance() {
        if (instance == null) {
            instance = new SalesDAO();
        }
        return instance;
    }
}
