import view.MainmenuView;

public class AppStart {
    // 프로그램의 진입점 main 메서드
    public static void main(String[] args) {
        // MainmenuView의 싱글톤 인스턴스를 가져와 start() 메서드 호출
        // 프로그램이 시작될 때 메인 메뉴를 보여줌
        MainmenuView.getInstance().start();

    } // main 함수 끝
} // AppStart 클래스 끝
