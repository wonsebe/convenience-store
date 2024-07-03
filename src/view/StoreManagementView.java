// StoreManagementView.java (View)
// 가게 관리와 관련된 기능들을 메뉴로
// 사용자가 선택한 메뉴에 따라 적절한 컨트롤러 메서드 호출

package view;

import controller.GameController;

public class StoreManagementView {
    private static StoreManagementView instance;

    private StoreManagementView() {}

    public static StoreManagementView getInstance() {
        if (instance == null) {
            instance = new StoreManagementView();
        }
        return instance;
    }
}
