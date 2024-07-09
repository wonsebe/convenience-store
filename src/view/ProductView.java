package view;

import controller.PcController;
import model.dto.InventoryLog;
import model.dto.Products;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

// 편의점 시뮬레이션 게임의 사용자 인터페이스를 담당하는 뷰 클래스
// 싱글톤 패턴을 사용해 구현
public class ProductView {
    // ANSI 이스케이프 코드 (텍스트 색상)
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // ANSI 이스케이프 코드 (배경 색상)
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    // ANSI 이스케이프 코드 (텍스트 스타일)
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final ProductView pView = new ProductView();

    // 사용자 입력을 받기 위한 Scanner 객체
    Scanner scan = new Scanner(System.in);

    // 현재 게임의 턴 수를 저장하는 변수
    int turn = 1;

    // private 생성자로 외부에서의 인스턴스 생성을 방지
    private ProductView() {
    }

    // 싱글톤 인스턴스를 반환하는 메서드
    public static ProductView getInstance() {
        return pView;
    }


    public void start() {
        System.out.print(

                "         ____________________________\n" +
                        "       .'                  '----'  '.\n" +
                        "      . \"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\" .\n" +
                        "     .   .\"\"\"\"\"\".   .--\"\"\"\"\"\"\"\"\"\"-,   .\n" +
                        "    . \"\"\".       \"\"'  .--\"\"\"\"\"--.. \"\"\" .\n" +
                        "   .\"\"\"\"\"'-\"\"\"\"\"-  .-'   |\"|\"|   .'\"\"\"\"\".\n" +
                        "  .   .''.'.     .'      |\"|\"|    .      .\n" +
                        "  '._( ()   \\\"\"\".  _     _\"\"\"  _   .____.'\n" +
                        "    |.'.  ()'   ' --------------------.|\n" +
                        "    ||  '--'\"\"\"\"\"'          |         ||\n" +
                        "    ||    '.------'     |\"\"|\"\"|\"\"|    ||\n" +
                        "    ||     |.-.-.||-----|--|--|--|----||\n" +
                        "    ||     || | |||     |__|_-\"-_|    ||\n" +
                        "    ||     ||_|_|||    .-\"-\" ()  '.   ||\n" +
                        "    || .--.| [-] ||   .' ()     () .  ||\n" +
                        "    |.'    '.    || .'\"\"\"\"\"\"\"\"\"\"\"\"\"'. ||\n" +
                        "    |: ()   |    ||--\\mga   .   .  /---|\n" +
                        "    /    () \\____||___\\___________/____|\n" +
                        "    '-------'\n");
        System.out.print("1. 시작하기 ");
        System.out.println("2. 종료");
        System.out.print("선택하세요: ");
        int choice = scan.nextInt();
        if (choice == 1) {
            index();
        } else {
            System.out.println("게임종료");
            System.exit(0); // 게임 종료
        }
    }


    // 게임의 메인 루프를 담당하는 메서드
    // 사용자 입력을 받아 해당하는 동작을 수행
    public void index() {
        // 인사말 출력
        System.out.print(YELLOW + "\n          (/ΩΩ/)\n" +
                                 "　     　 / •• /\n" +
                                 "　　    　(＿ノ |  " + GREEN + "어서오세요 jSS\n" + YELLOW +
                                 "　　    　　 |　|" + GREEN + "       편의점 입니다!★\n" + YELLOW +
                                 "　　    　　 |　|\n" +
                                 "　　    　 __|　|＿\n" +
                                 "　    　　/ヘ　　/ )\n" +
                                 "　　    　L ニニコ/\n" +
                                 "　　    　|￣￣￣ |\n" +
                                 "　　    　|　　 　|――≦彡\n" +
                                 "　　    　|　∩　 |\n" +
                                 "　　    　|　||　|\n" +
                                 "　　    　|　||　|\n" +
                                 "　　    　|二||二|\n" + RESET);

        while (true) {
            // 현재 모든 상품의 재고 상태를 출력 (비활성화)
            // displayInventory();

            // 사용자 행동 선택 메뉴 출력 및 입력 받기
            System.out.print(CYAN + "1" + RESET + " - 재고 구매\t\t");
            System.out.print(CYAN + "2" + RESET + " - 재고 확인\t\t");
            System.out.print(CYAN + "3" + RESET + " - 상품 추가\t\t");
            System.out.print(CYAN + "4" + RESET + " - 상품 수정\t\t");
            System.out.println();
            System.out.print(CYAN + "5" + RESET + " - 재고 삭제\t\t");
            System.out.print(CYAN + "6" + RESET + " - 물품 확인\t\t");
            System.out.print(CYAN + "7" + RESET + " - ㅇㅇㅇㅇ\t\t");
            System.out.print(CYAN + "8" + RESET + " - ㅇㅇㅇㅇ\t\t");
            System.out.println();
            System.out.print(CYAN + "9" + RESET + " - ㅇㅇㅇㅇ\t\t");
            System.out.print(CYAN + "10" + RESET + " - ㅇㅇㅇㅇ\t\t");
            System.out.print(CYAN + "11" + RESET + " - ㅇㅇㅇㅇ\t\t");
            System.out.print(CYAN + "12" + RESET + " - ㅇㅇㅇㅇ\t\t");
            System.out.println();

            // 다음 턴 및 종료는 최하단에 표시
            System.out.print("=============\t" + CYAN + "99" + RESET + " - 다음 턴\t\t");
            System.out.print(CYAN + "100" + RESET + " - 게임 종료" + "\t=============");
            System.out.println();

            try {
                // 새 턴 시작
                System.out.print("행동 선택: ");
                int choice = scan.nextInt();

                // 향상된 switch 문을 사용해 사용자 선택에 따른 동작 수행
                switch (choice) {
                    case 1 -> supplyRestock();  // 재고 구매
                    case 2 -> displayInventory();  // 재고 확인
                    case 3 -> addProduct();  // 상품 추가
                    case 4 -> updateProduct();  // 상품 수정
                    case 5 -> pDelete(); // 재고 삭제
                    case 6 -> pPrint(); // 물품 확인
                    case 7 -> inrush(); //강도 침입 함수
                    case 99 -> processTurn();  // 다음 턴 진행
                    case 100 -> {
                        System.out.println("게임을 종료합니다.");  // 게임 종료
                        start(); // 메서드 종료
                    }
                    default -> System.out.println("잘못된 선택입니다. 다시 선택해주세요."); // 잘못된 입력 처리
                } // switch 끝
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scan.next(); // 잘못된 입력을 버리고 넘어가기
            } // try 끝
        } // while 끝
    } // 게임의 메인 루프를 담당하는 메서드 end

    // 1 - 재고 구매 메서드
    public void supplyRestock() {
        // 구매할 제품 번호를 입력한다
        System.out.println("입고할 제품 번호를 입력하세요.");
        System.out.print(">>");
        int pId = scan.nextInt();
        // 구매할 제품의 수량을 입력한다
        System.out.println("입고할 수량을 입력하세요.");
        System.out.print(">>");
        int quantity = scan.nextInt();
        // 제품번호와 수량을 컨트롤러에 넘겨 여러 절차를 검증한다
        int previousBalance = PcController.getInstance().getStoreBalance();
        String result = PcController.getInstance().supplyRestock(pId, quantity, turn);
        int currentBalance = PcController.getInstance().getStoreBalance();

        // 절차 검증이 완료되고 해당하는 결과 문자열 출력
        System.out.println(result);
        System.out.println("이전 잔고: " + previousBalance + "원");
        System.out.println("현재 잔고: " + currentBalance + "원");
    } // 1 - 재고 구매 메서드 end

    // 2 - 재고 확인 메서드
    public void displayInventory() {
        for (int i = 1; i <= PcController.getInstance().getProductTypeCount(); i++) {
            System.out.print("상품번호: " + i);
            int inventory = PcController.getInstance().checkInventory(i);
            System.out.println("       재고 = " + inventory);
        }
    } // 현재 모든 상품의 재고 상태를 출력하는 메서드 end


    // 3 - 상품 추가 메서드
    // 사용자로부터 상품명, 가격, 유통기한을 입력받아 새 상품을 생성
    public void addProduct() {
        System.out.println("상품 추가 페이지");
        scan.nextLine(); // 버퍼 비우기
        System.out.print("상품명: ");
        String name = scan.nextLine();
        System.out.print("가격: ");
        int price = scan.nextInt();
        System.out.print("유통기한(턴): ");
        int expiryTurns = scan.nextInt();

        Products newProduct = new Products();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setExpiryTurns(expiryTurns);

        boolean result = PcController.getInstance().addProduct(newProduct);

        if (result) {
            System.out.println("상품 추가 성공!");
        } else {
            System.out.println("상품 추가 실패");
        }
    } // 3 - 상품 추가 메서드 end

    // 4 - 상품 수정 메서드
    // 사용자로부터 상품 ID와 새로운 가격을 입력받아 상품 정보 업데이트
    public void updateProduct() {
        System.out.println("상품 수정 페이지");
        System.out.print("수정할 상품 ID: ");
        int productId = scan.nextInt();
        System.out.print("새로운 가격: ");
        int newPrice = scan.nextInt();

        Products updatedProduct = new Products();
        updatedProduct.setProductId(productId);
        updatedProduct.setPrice(newPrice);

        boolean result = PcController.getInstance().updateProduct(updatedProduct);

        if (result) {
            System.out.println("상품 수정 성공!");
        } else {
            System.out.println("상품 수정 실패");
        }
    } // 4 - 상품 수정 메서드 end

    // 5 - 재고 삭제 메서드
    // 수정이 필요한 메서드입니다 (세원 코멘트)
    public void pDelete() {
        // 삭제할 제품 번호를 입력받기
        System.out.println("삭제 페이지");
        System.out.print("삭제할 제품번호를 입력해주세요: ");
        int productId = scan.nextInt();

        // PcController에서 pDelete 함수 호출
        boolean result = PcController.getInstance().pDelete(productId);

        // 삭제 결과에 따른 메시지 출력
        if (result) {
            System.out.println("삭제 성공!");

        } else {
            System.out.println("삭제 실패");

        }
    } // 5 - 재고 삭제 메서드 end

    // 6 - 물품 확인 메서드
    public void pPrint() {
        ArrayList<Products> result = PcController.getInstance().pPrint();
        System.out.println("제품번호\t\t\t제품명\t\t\t 제품가격\t\t제품수량\t\t유통기한");
        result.forEach(dto -> {
            System.out.printf("%2d\t%15s\t\t%10s\t\t%10d\t%10d\n", dto.getProductId(), dto.getName(), dto.getPrice(), dto.getStock(), dto.getExpiryTurns());
        });
    } // 6 - 물품 확인 메서드 end

    // 99 - 다음 턴 진행 메서드
    public void processTurn() {
        System.out.println(turn + "번째 턴을 진행합니다.");
        // 턴을 넘기면 진행되는 여러 사건들을 메서드로 만들고 99.X 번호로 구분
        PcController.getInstance().processPurchaseAndSales(turn);
        ArrayList<InventoryLog> logs = PcController.getInstance().purchase(turn);
        simulateCustomerVisits(logs); // 99.1 - 손님 방문 메서드
        displayTotalSalesAndBalance(); // 총 매출액 출력
        // 월세 차감 처리
        boolean rentPaid = PcController.getInstance().deductRent(turn);
        if (!rentPaid) {
            gameOver("월세를 낼 돈이 부족합니다.");
            return;
        }

        // 승리조건
        if (turn >= 100) {
            System.out.println("게임 승리");
        }

        // 이벤트 함수( 강도 )
        // 1. 난수
        int rand = new Random().nextInt(100) + 1; // 1 ~ 100 난수 생성
        // 2. 1~100 중 50 이하 이면 5:5
        if (rand <= 30) {
            inrush();
        }
        turn++; // 턴 증가
        checkLoseCondition();
    } // 99 - 다음 턴 진행 메서드 end

    // 99.1 - 손님 방문 메서드
    public void simulateCustomerVisits(ArrayList<InventoryLog> logs) {
        if (logs.isEmpty()) {
            System.out.println("이번 턴에는 손님이 없었습니다.");
        } else {
            for (InventoryLog log : logs) {
                String productName = PcController.getInstance().getProductName(log.getProductId());
                int currentInventory = PcController.getInstance().checkInventory(log.getProductId());
                if (log.getQuantity() != 0) {  // 구매 성공 시 quantity는 음수 값
                    if (currentInventory == 0) {
                        System.out.printf("손님이 %s을(를) %d개 구매했습니다. (현재 재고: %s%d%s)%n",
                                          productName, -log.getQuantity(), RED, currentInventory, RESET
                        );
                        // null 뜰때 확인용 콘솔
                        // System.out.println(log);
                    } else if (currentInventory <= 5) {
                        System.out.printf("손님이 %s을(를) %d개 구매했습니다. (현재 재고: %s%d%s)%n",
                                          productName, -log.getQuantity(), YELLOW, currentInventory, RESET
                        );
                        // null 뜰때 확인용 콘솔
                        // System.out.println(log);
                    } else {
                        System.out.printf("손님이 %s을(를) %d개 구매했습니다. (현재 재고: %d)%n",
                                          productName, -log.getQuantity(), currentInventory
                        );
                        // null 뜰때 확인용 콘솔
                        // System.out.println(log);
                    }
                } else {
                    System.out.printf("손님이 %s을(를) 사려고 했으나 %s재고가 부족%s하여 구매하지 못했습니다. (현재 재고: %s%d%s)%n",
                                      productName, RED, RESET, RED, currentInventory, RESET
                    );
                    // null 뜰때 확인용 콘솔
                    // System.out.println(log);
                } // if 끝
            } // for 끝
        } // if 끝
    } // 99.1 - 손님 방문 메서드 end

    // 99.2 - 이벤트: 강도가 들어 재고를 털어가는 설정 -재고 랜덤으로 깎임(수량이 깎이는 설정 -재고가 아예 없어지지는 않음)
    //어떤 상품을, 몇개 빼앗아 가는지 inventory log 기록 함수를 사용해서 하기
    public void inrush() {

        System.out.println("강도가 침입했습니다!");
        //PcController에서 turn을 매개변수로 하여 무언가를 구매하고, 그 구매에 대한 인벤토리 로그를 담은 ArrayList를 반환.
        ArrayList<InventoryLog> still = PcController.getInstance().purchase(turn);

        //still 리스트에 있는 각 인벤토리 로그를 순회하면서, 각각의 상품에 대해 이름과 재고를 확인
        for (InventoryLog stills : still) { //still 리스트에서 InventoryLog 객체를 하나씩 가져와서 stills라는 변수에 할당
            String productName = PcController.getInstance().getProductName(stills.getProductId());
            //해당 상품 ID에 대한 상품 이름을 가져옴 ,stills 객체가 가리키는 상품의 이름을   productName 문자열 변수에 할당
            int currentInventory = PcController.getInstance().checkInventory(stills.getProductId());
            //해당 상품 ID에 대한 재고 수량을 확인, 해당 상품의 재고를 currentInventory 변수에 할당
            if (stills.getQuantity() != 0) { //stills라는 변수가 참조한 수량이 0개가 아니라는 경우를 듦.
                System.out.printf("강도가 %s을(를) %d개 훔쳐갔습니다. (남은 재고: %d) %n",
                                  //강도가 어떤 제품을 몇 개 훔쳐갔는지 안내
                                  productName, -stills.getQuantity(), currentInventory
                ); //제품이름과 감소된 수량, 기록용 로그를 알려주기 위해 선언
            } else { //강도가 침입했어도 가져가지 못한 경우를 듦.
                System.out.printf(
                        "★강도가 %s을(를) 훔치려다가 인기척을 느끼고 도망갔습니다!★  ",
                        productName
                );
            }
            // 강도 함수 호출
            PcController.getInstance().inrush(); //PcController의 inrush를 호출함

        } //for end

    }//강도함수 end

    // 99.2 - 강도 침입 메서드 end


    // 턴의 총 매출액과 잔고를 구하는 메서드
    public void displayTotalSalesAndBalance() {
        int totalSales = PcController.getInstance().getLastTurnTotalSales();
        int storeBalance = PcController.getInstance().getStoreBalance();
        System.out.println(YELLOW + "이번 턴의 총 매출액: " + totalSales + "원" + RESET);
        System.out.println(GREEN + "편의점 현재 잔고: " + storeBalance + "원" + RESET);
    } // 턴의 총 매출액과 잔고를 구하는 메서드 end


    //패배조건 : 10개 물품의 재고가 0개되면 탈락
    public void checkLoseCondition() {

        int ZeroInventory = 0; //현재 재고가 0인 상품의 개수를 세는 변수 ZeroInventory 0으로 초기화

        int totalProductTypes = PcController.getInstance().getProductTypeCount();
        //PcController에 등록된 상품 종류 수 반환 메서드 getProductTypeCount 를 참조하여  상품 종류 수를 반복하여 처리하거나,
        // 특정 상품의 재고를 확인하는 totalProductTypes에 대입

        for (int i = 1; i <= totalProductTypes; i++) {
            int inventory = PcController.getInstance().checkInventory(i);
            //재고 확인 메서드의(checkInventory) i번째에 있는 물품의 수를  inventory 변수에 대입
            //inventory 변수에는 PcController 클래스를 통해 조회한 특정 상품의 재고가 할당

            if (inventory == 0) {//만약에 재고가 없다면
                ZeroInventory++; //재고가 0인 상품의 개수를 세기 위해 ( 몇종류의 재고가 0인지 카운트)
                if (ZeroInventory >= 10) { //재고 종류가 10개 이상이면
                    gameOver("게임 오버: 10개의 물품 재고가 모두 소진되었습니다."); // 패배 조건 충족 시 게임 오버 처리
                    return; //초기화면으로 돌아가기
                }
            } // if end
        } // for end
    } // checkLoseCondition end

    public void gameOver(String reason) { //제품종류가 10개 이상이 0개면 게임오버를 알리는 함수
        System.out.println(ProductView.RED + "게임 오버: " + reason + ProductView.RESET);
        System.out.println("   ______    ______   __       __  ________         ______   __     __  ________  _______  \n" +
                                   " /      \\  /      \\ /  \\     /  |/        |       /      \\ /  |   /  |/        |/       \\ \n" +
                                   "/$$$$$$  |/$$$$$$  |$$  \\   /$$ |$$$$$$$$/       /$$$$$$  |$$ |   $$ |$$$$$$$$/ $$$$$$$  |\n" +
                                   "$$ | _$$/ $$ |__$$ |$$$  \\ /$$$ |$$ |__          $$ |  $$ |$$ |   $$ |$$ |__    $$ |__$$ |\n" +
                                   "$$ |/    |$$    $$ |$$$$  /$$$$ |$$    |         $$ |  $$ |$$  \\ /$$/ $$    |   $$    $$< \n" +
                                   "$$ |$$$$ |$$$$$$$$ |$$ $$ $$/$$ |$$$$$/          $$ |  $$ | $$  /$$/  $$$$$/    $$$$$$$  |\n" +
                                   "$$ \\__$$ |$$ |  $$ |$$ |$$$/ $$ |$$ |_____       $$ \\__$$ |  $$ $$/   $$ |_____ $$ |  $$ |\n" +
                                   "$$    $$/ $$ |  $$ |$$ | $/  $$ |$$       |      $$    $$/    $$$/    $$       |$$ |  $$ |\n" +
                                   " $$$$$$/  $$/   $$/ $$/      $$/ $$$$$$$$/        $$$$$$/      $/     $$$$$$$$/ $$/   $$/ ");
        start(); // 초기화면으로 돌아감

    }//게임오버 함수 end


} // ProductView 클래스 종료


