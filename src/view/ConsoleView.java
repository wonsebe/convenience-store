// ConsoleView.java (View)
// 콘솔을 통해 사용자와 상호작용
// 사용자에게 메시지를 출력하는 메서드
// 사용자로부터 입력을 받는 메서드

package view;

public class ConsoleView {
    private static ConsoleView instance;

    private ConsoleView() {}

    public static ConsoleView getInstance() {
        if (instance == null) {
            instance = new ConsoleView();
        }
        return instance;
    }
}
