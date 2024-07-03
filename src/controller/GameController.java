// GameController.java
// 게임 전체 흐름 제어
// 게임 시작, 진행, 종료 등 주요 기능 담당

package controller;

import model.Game;
import view.MainMenuView;
import view.StoreManagementView;
import view.ConsoleView;

public class GameController {
    private static GameController instance;
    private Game game;
    private MainMenuView mainMenuView;
    private StoreManagementView storeManagementView;
    private ConsoleView consoleView;

    private GameController() {
        // private 생성자
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void initialize(Game game, MainMenuView mainMenuView, StoreManagementView storeManagementView, ConsoleView consoleView) {
        if (this.game == null) {
            this.game = game;
            this.mainMenuView = mainMenuView;
            this.storeManagementView = storeManagementView;
            this.consoleView = consoleView;
        }
    }

    public void startGame() {
        boolean running = true;
        mainMenuView.displayMainMenu();

        while (running) {
            int choice = mainMenuView.getMenuChoice();
            switch (choice) {
                case 1:
                    runGameLoop();
                    break;
                case 2:
                    running = false;
                    break;
                default:
                    consoleView.displayMessage("유효하지 않은 선택입니다.");
            }
        }
    }

    private void runGameLoop() {
        boolean gameRunning = true;

        while (gameRunning) {
            storeManagementView.displayStoreManagementMenu();
            int choice = storeManagementView.getManagementChoice();

            switch (choice) {
                case 1:
                    // 상품 관리 로직 추가
                    break;
                case 2:
                    // 직원 관리 로직 추가
                    break;
                case 3:
                    // 매출 보고서 로직 추가
                    break;
                case 4:
                    gameRunning = false;
                    break;
                default:
                    consoleView.displayMessage("유효하지 않은 선택입니다.");
            }

            // 게임의 다음 턴으로 이동
            game.nextTurn();
        }
    }
}