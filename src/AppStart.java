// ProductView 클래스를 import

import view.ProductView;

// 애플리케이션의 시작점을 정의하는 클래스
public class AppStart {
    // 프로그램의 진입점 main 메서드
    public static void main(String[] args) {
        // ProductView의 싱글톤 인스턴스를 가져와 index() 메서드 호출
        ProductView.getInstance().start();
    } // main 함수 끝
} // AppStart 클래스 끝
