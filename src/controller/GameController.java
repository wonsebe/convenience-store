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

    // 싱글톤 패턴 전역접근 가능한 getInstance 메서드 (인스턴스 반환)
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    } // getInstance 끝

    public void initialize(Game game, MainMenuView mainMenuView, StoreManagementView storeManagementView, ConsoleView consoleView) {
        if (this.game == null) {
            this.game = game;
            this.mainMenuView = mainMenuView;
            this.storeManagementView = storeManagementView;
            this.consoleView = consoleView;
        } // if문 끝
    } // initialize (초기화) 함수 끝

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
            } // switch 끝
        } // while 반복문 끝
    } // startGame 함수 끝

    private void runGameLoop() {
        boolean gameRunning = true;

        while (gameRunning) {
            storeManagementView.displayStoreManagementMenu();
            int choice = storeManagementView.getManagementChoice();

            switch (choice) {
                case 0:
                    gameRunning = false;
                    System.out.println("게임을 종료합니다.");
                    break;
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
                    // 게임의 다음 턴으로 이동
                    game.nextTurn();
                    System.out.println("다음 턴으로 넘어갑니다.");
                    break;
                default:
                    consoleView.displayMessage("유효하지 않은 선택입니다.");
            } // switch 끝
        } // while 반복문 끝
    } // runGameLoop 함수 끝
} // Game Controller 클래스 끝