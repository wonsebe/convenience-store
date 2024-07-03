// 판매 기록 관리, 매출 데이터 저장
// 일별, 주별, 월별 매출 통계 계산 및 보고서 생성
// 판매 데이터 바탕으로 매출 분석 수행
// recordSale()
// 입력 매개변수: 판매된 상품의 ID, 판매 수량, 판매 가격 등의 정보
// 출력: 판매가 성공적으로 기록되었는지 여부
// 판매가 발생할 때마다 해당 판매를 기록, 관련 데이터를 데이터베이스에 저장
// generateSalesReport()

package controller;

public class SalesController {
    private static SalesController instance;

    private SalesController() {}

    public static SalesController getInstance() {
        if (instance == null) {
            instance = new SalesController();
        }
        return instance;
    }
}
