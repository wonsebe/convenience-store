import controller.EmployeeController;
import controller.GameController;
import controller.ProductController;
import controller.SalesController;
import model.Database;
import model.Game;
import model.Store;
import model.dto.Customer;
import model.dto.Employee;
import view.ConsoleView;
import view.MainMenuView;
import view.StoreManagementView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppStart {
    public static void main(String[] args) {
        // 데이터베이스 연결 초기화
        Connection connection;
        try {
            Database database = Database.getInstance();
            connection = database.getConnection();
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 실패: " + e);
            return;
        }

        // 모델 초기화
        Store store = Store.getInstance();
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        Game game = Game.getInstance();

        // 뷰 초기화
        ConsoleView consoleView = ConsoleView.getInstance();
        MainMenuView mainMenuView = MainMenuView.getInstance();
        StoreManagementView storeManagementView = StoreManagementView.getInstance();

        // 컨트롤러 초기화
        GameController gameController = GameController.getInstance();
        gameController.initialize(game, mainMenuView, storeManagementView, consoleView);
        ProductController productController = ProductController.getInstance();
        EmployeeController employeeController = EmployeeController.getInstance();
        SalesController salesController = SalesController.getInstance();

        // 게임 실행
        gameController.startGame();

        // 데이터베이스 연결 종료
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 종료 실패: " + e.getMessage());
        }
    }
}