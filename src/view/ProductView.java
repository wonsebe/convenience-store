package view;

import controller.PcController;
import model.dao.InventoryDao;
import model.dto.BoardDto;
import model.dto.InventoryLog;
import model.dto.Products;
import util.ColorUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

// 편의점 시뮬레이션 게임의 사용자 인터페이스를 담당하는 뷰 클래스
// 싱글톤 패턴을 사용해 구현
public class ProductView {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final ProductView pView = new ProductView();

    // 사용자 입력을 받기 위한 Scanner 객체
    Scanner scan = new Scanner(System.in);

    // private 생성자로 외부에서의 인스턴스 생성을 방지
    private ProductView() {
    }

    // 싱글톤 인스턴스를 반환하는 메서드
    public static ProductView getInstance() {
        return pView;
    }

    // 게임의 메인 루프를 담당하는 메서드
    // 사용자 입력을 받아 해당하는 동작을 수행
    public void index() {
        // 아래는 index() 메서드 시작시 맨 처음에 있어야 함 (수정시 주의)
        PcController.getInstance().loadGameState();  // 게임 시작 시 상태 로드
        // 인사말 출력
        System.out.print(ColorUtil.getColor("YELLOW") + "\n          (/ΩΩ/)\n" +
                "　     　 / •• /\n" +
                "　　    　(＿ノ |  " + ColorUtil.getColor("GREEN") + "어서오세요 jSS\n" + ColorUtil.getColor("YELLOW") +
                "　　    　　 |　|" + ColorUtil.getColor("GREEN") + "       편의점 입니다!★\n" + ColorUtil.getColor("YELLOW") +
                "　　    　　 |　|\n" +
                "　　    　 __|　|＿\n" +
                "　    　　/ヘ　　/ )\n" +
                "　　    　L ニニコ/\n" +
                "　　    　|￣￣￣ |\n" +
                "　　    　|　　 　|――≦彡\n" +
                "　　    　|　∩　 |\n" +
                "　　    　|　||　|\n" +
                "　　    　|　||　|\n" +
                "　　    　|二||二|\n" + ColorUtil.getColor("RESET"));

        while (true) {
            int currentTurn = PcController.getInstance().getTurn();
            System.out.println("현재 턴: " + currentTurn);
            // 사용자 행동 선택 메뉴 출력 및 입력 받기
            System.out.print(ColorUtil.getColor("CYAN") + "1" + ColorUtil.getColor("RESET") + " - 재고 구매\t\t");
            System.out.print(ColorUtil.getColor("CYAN") + "2" + ColorUtil.getColor("RESET") + " - 재고 확인\t\t");
            System.out.print(ColorUtil.getColor("CYAN") + "3" + ColorUtil.getColor("RESET") + " - 상품 추가\t\t");
            System.out.print(ColorUtil.getColor("CYAN") + "4" + ColorUtil.getColor("RESET") + " - 상품 가격 수정\t\t");
            System.out.println();
            System.out.print(ColorUtil.getColor("CYAN") + "5" + ColorUtil.getColor("RESET") + " - 상품 삭제\t\t");
            System.out.print(ColorUtil.getColor("CYAN") + "6" + ColorUtil.getColor("RESET") + " - 공지 쓰기\t\t");
            System.out.print(ColorUtil.getColor("CYAN") + "7" + ColorUtil.getColor("RESET") + " - 공지 보기\t\t");

            System.out.println();

            // 다음 턴 및 종료는 최하단에 표시
            System.out.print("=============\t" + ColorUtil.getColor("CYAN") + "99" + ColorUtil.getColor("RESET") + " - 다음 턴\t\t");
            System.out.print(ColorUtil.getColor("CYAN") + "100" + ColorUtil.getColor("RESET") + " - 게임 종료" + "\t=============");
            System.out.println();

            try {
                // 새 턴 시작
                System.out.print("행동 선택: ");
                int choice = scan.nextInt();

                // 향상된 switch 문을 사용해 사용자 선택에 따른 동작 수행
                switch (choice) {
                    case 1 -> supplyRestock();  // 재고 구매
                    case 2 -> pPrint();         // 물품 확인
                    case 3 -> addProduct();  // 상품 추가
                    case 4 -> updateProduct();  // 상품 수정
                    case 5 -> pDelete(); // 재고 삭제
                    case 6 -> writeNotice();    // 공지 쓰기
                    case 7 -> viewNotices();    // 공지 확인
                    case 99 -> processTurn();  // 다음 턴 진행
                    case 100 -> {
                        System.out.println("게임을 종료합니다.");  // 게임 종료
                        MainmenuView.getInstance().start(); // 메인메뉴로 이동
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
        int currentTurn = PcController.getInstance().getTurn(); // 현재 턴 정보 가져오기
        // 구매할 제품 번호를 입력한다
        System.out.println("입고할 제품 번호를 입력하세요.");
        System.out.print(">>");
        int pId = scan.nextInt();
        // 구매할 제품의 수량을 입력한다
        System.out.println("입고할 수량을 입력하세요.");
        System.out.print(">>");
        int quantity = scan.nextInt();

        // 현재 잔고 확인
        int currentBalance = PcController.getInstance().getStoreBalance();

        System.out.println("현재 잔고: " + currentBalance + "원");
        System.out.print("구매를 진행하시겠습니까? (y/n): ");

        String confirm = scan.next().toLowerCase();
        if (confirm.equals("y")) {
            // 컨트롤러의 supplyRestock 메서드 호출
            String result = PcController.getInstance().supplyRestock(pId, quantity, currentTurn);

            // 결과 출력
            System.out.println(result);

            // 구매 후 실제 잔고 출력
            int newBalance = PcController.getInstance().getStoreBalance();
            System.out.println("구매 후 실제 잔고: " + newBalance + "원");
        } else {
            System.out.println("구매가 취소되었습니다.");
        }
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
        int currentTurn = PcController.getInstance().getTurn();
        System.out.println("제품번호\t\t\t제품명\t\t제품가격\t\t제품수량\t\t남은 유통기한\t\t상태");
        result.forEach(dto -> {
            int remainingTurns = dto.getExpiryTurns() - (currentTurn - dto.getGameDate());
            String status = remainingTurns <= 3 ? ColorUtil.getColor("RED") + "임박" + ColorUtil.getColor("RESET") :
                    remainingTurns <= 5 ? ColorUtil.getColor("YELLOW") + "주의" + ColorUtil.getColor("RESET") : "정상";
            System.out.printf("%2d\t%15s\t%10s\t%10d\t%10d\t%10s\n",
                    dto.getProductId(), dto.getName(), dto.getPrice(), dto.getStock(),
                    remainingTurns > 0 ? remainingTurns : 0, status
            );
        });
    } // 6 - 물품 확인 메서드 end

    // 8 - 글쓰기
    private void writeNotice() {
        System.out.println("공지 내용을 입력하세요:");
        scan.nextLine(); // 버퍼 비우기
        String content = scan.nextLine();

        boolean result = PcController.getInstance().addNotice(content);
        if (result) {
            System.out.println(ColorUtil.getColor("GREEN") + "공지가 성공적으로 작성되었습니다." + ColorUtil.getColor("RESET"));
        } else {
            System.out.println(ColorUtil.getColor("RED") + "공지 작성에 실패했습니다." + ColorUtil.getColor("RESET"));
        }
    }

    // 9 - 글보기
    private void viewNotices() {
        ArrayList<BoardDto> notices = PcController.getInstance().getAllNotices();
        if (notices.isEmpty()) {
            System.out.println(ColorUtil.getColor("YELLOW") + "등록된 공지사항이 없습니다." + ColorUtil.getColor("RESET"));
        } else {
            System.out.println("====== 공지사항 목록 ======");
            System.out.printf("%-5s %-15s %-30s %-10s\n", "번호", "작성일", "내용", "작성자");
            for (BoardDto notice : notices) {
                System.out.printf(
                        "%-5d %-15s %-30s %-10d\n",
                        notice.getBmo(),
                        notice.getBdate(),
                        notice.getBcontent().length() > 30 ? notice.getBcontent().substring(0, 27) + "..." : notice.getBcontent(),
                        notice.getStore_id()
                );
            }
            System.out.println("==========================");
        }
    }

    // 99 - 다음 턴 진행 메서드
    public void processTurn() {
        int currentTurn = PcController.getInstance().getTurn();
        System.out.println(currentTurn + "번째 턴을 진행합니다.");

        // 유통기한 체크 및 폐기 처리
        PcController.getInstance().checkAndRemoveExpiredInventory();

        // 기존 턴 처리 로직
        PcController.getInstance().processPurchaseAndSales(currentTurn);
        ArrayList<InventoryLog> logs = PcController.getInstance().purchase(currentTurn);
        simulateCustomerVisits(logs);
        displayTotalSalesAndBalance();

        // 월세 차감 처리
        boolean rentPaid = PcController.getInstance().deductRent(currentTurn);
        if (!rentPaid) {
            gameOver("월세를 낼 돈이 부족합니다.");
            return;
        }

        // 승리조건
        if (currentTurn >= 100) {
            System.out.println("게임 승리");
            return;  // 게임 승리 시 메서드 종료
        }

        // 이벤트 함수( 강도 )
        // 1. 난수
        int randInrush = new Random().nextInt(99) + 1; // 1 ~ 100 난수 생성
        int randBread = new Random().nextInt(99) + 1; // 1 ~ 100 난수 생성
        // 2. 1~100 중 50 이하 이면 5:5
        if (randInrush <= 30) {
            inrush();
        }
        else if (randBread <= 30) {
            bread1();
        }

        checkLoseCondition();

        // 턴 증가
        PcController.getInstance().setTurn(currentTurn + 1);
    } // 99 - 다음 턴 진행 메서드 end

    // 99.1 - 손님 방문 메서드
    public void simulateCustomerVisits(ArrayList<InventoryLog> logs) {
        if (logs.isEmpty()) {
            System.out.println("이번 턴에는 손님이 없었습니다.");
        } else {
            for (InventoryLog log : logs) {
                String productName = PcController.getInstance().getProductName(log.getProductId());
                int currentInventory = PcController.getInstance().checkInventory(log.getProductId());
                if (log.getQuantity() < InventoryDao.getInstance().checkInventory(log.getProductId())) {  // 구매 성공 시 quantity는 음수 값
                    if (currentInventory == 0) {
                        System.out.printf("손님이 %s을(를) %d개 구매했습니다. (현재 재고: %s%d%s)%n",
                                productName, -log.getQuantity(), ColorUtil.getColor("RED"), currentInventory, ColorUtil.getColor("RESET")
                        );
                        // null 뜰때 확인용 콘솔
                        // System.out.println(log);
                    } else if (currentInventory <= 5) {
                        System.out.printf("손님이 %s을(를) %d개 구매했습니다. (현재 재고: %s%d%s)%n",
                                productName, -log.getQuantity(), ColorUtil.getColor("YELLOW"), currentInventory, ColorUtil.getColor("RESET")
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
                            productName, ColorUtil.getColor("RED"), ColorUtil.getColor("RESET"), ColorUtil.getColor("RED"), currentInventory, ColorUtil.getColor("RESET")
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
        int currentTurn = PcController.getInstance().getTurn(); // 현재 턴 정보 가져오기
        System.out.println("＿人人人人人人人人人＿\n" +
                "＞살금살금살금살금살금＜\n" +
                "￣ＹＹＹＹＹＹＹＹＹ￣\n" +
                "\n" +
                "　　　　ハ_ハ\n" +
                "　／＼（´◔౪◔）／＼\n" +
                "((⊂  ／＼　　　／＼  つ))\n" +
                "　　　　)　　ノ\n" +
                "　　　　(＿⌒ヽ\n" +
                "　　　　ヽ  ヘ   |\n" +
                "　　　　 ノノ  Ｊ\n");
        System.out.println("강도가 침입했습니다!");
        //PcController에서 turn을 매개변수로 하여 무언가를 구매하고, 그 구매에 대한 인벤토리 로그를 담은 ArrayList를 반환.
        ArrayList<InventoryLog> still = PcController.getInstance().purchase(currentTurn);

        //still 리스트에 있는 각 인벤토리 로그를 순회하면서, 각각의 상품에 대해 이름과 재고를 확인
        for (InventoryLog stills : still) { //still 리스트에서 InventoryLog 객체를 하나씩 가져와서 stills라는 변수에 할당
            String productName = PcController.getInstance().getProductName(stills.getProductId());
            //해당 상품 ID에 대한 상품 이름을 가져옴 ,stills 객체가 가리키는 상품의 이름을   productName 문자열 변수에 할당
            int currentInventory = PcController.getInstance().checkInventory(stills.getProductId());
            //해당 상품 ID에 대한 재고 수량을 확인, 해당 상품의 재고를 currentInventory 변수에 할당
            if (stills.getQuantity() < InventoryDao.getInstance().checkInventory(stills.getProductId())) { //stills라는 변수가 참조한 수량이 0개가 아니라는 경우를 듦.
                System.out.printf("강도가 %s을(를) %d개 훔쳐갔습니다. (남은 재고: %d) %n ",
                        //강도가 어떤 제품을 몇 개 훔쳐갔는지 안내
                        productName, -stills.getQuantity(), currentInventory
                ); //제품이름과 감소된 수량, 기록용 로그를 알려주기 위해 선언
            } else { //강도가 침입했어도 가져가지 못한 경우를 듦.
                System.out.printf(
                        "★강도가 %s을(를) 훔치려다가 인기척을 느끼고 도망갔습니다!★ \n ",
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
        System.out.println("=========================================================");
        int totalSales = PcController.getInstance().getLastTurnTotalSales();
        int storeBalance = PcController.getInstance().getStoreBalance();
        System.out.println(ColorUtil.getColor("YELLOW") + "이번 턴의 총 매출액: " + totalSales + "원" + ColorUtil.getColor("RESET"));
        System.out.println(ColorUtil.getColor("GREEN") + "편의점 현재 잔고: " + storeBalance + "원" + ColorUtil.getColor("RESET"));
        System.out.println("=========================================================");
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
        System.out.println(ColorUtil.getColor("RED") + "게임 오버: " + reason + ColorUtil.getColor("RESET"));
        System.out.println("   ______    ______   __       __  ________         ______   __     __  ________  _______  \n" +
                " /      \\  /      \\ /  \\     /  |/        |       /      \\ /  |   /  |/        |/       \\ \n" +
                "/$$$$$$  |/$$$$$$  |$$  \\   /$$ |$$$$$$$$/       /$$$$$$  |$$ |   $$ |$$$$$$$$/ $$$$$$$  |\n" +
                "$$ | _$$/ $$ |__$$ |$$$  \\ /$$$ |$$ |__          $$ |  $$ |$$ |   $$ |$$ |__    $$ |__$$ |\n" +
                "$$ |/    |$$    $$ |$$$$  /$$$$ |$$    |         $$ |  $$ |$$  \\ /$$/ $$    |   $$    $$< \n" +
                "$$ |$$$$ |$$$$$$$$ |$$ $$ $$/$$ |$$$$$/          $$ |  $$ | $$  /$$/  $$$$$/    $$$$$$$  |\n" +
                "$$ \\__$$ |$$ |  $$ |$$ |$$$/ $$ |$$ |_____       $$ \\__$$ |  $$ $$/   $$ |_____ $$ |  $$ |\n" +
                "$$    $$/ $$ |  $$ |$$ | $/  $$ |$$       |      $$    $$/    $$$/    $$       |$$ |  $$ |\n" +
                " $$$$$$/  $$/   $$/ $$/      $$/ $$$$$$$$/        $$$$$$/      $/     $$$$$$$$/ $$/   $$/ ");
        MainmenuView.getInstance().start(); // 초기화면으로 돌아감

    }//게임오버 함수 end


    //굿이벤트 (포켓몬빵)
    //랜덤으로 포켓몬 빵이 들어오는 날이 오면 손님 50명이 우르르 몰려서 재고를 사는 함수
    public void bread1(){
        System.out.println("포켓몬 빵이 들어왔습니다!");
        //편의점 포켓몬빵 입고
        PcController.getInstance().bread1();
        // 손님 수를 랜덤으로 설정
        int numCustomers = new Random().nextInt(21); // 0부터 20명 사이의 랜덤 손님 수
        numCustomers += 30; //30에서 50

        // 손님들이 포켓몬 빵을 최대 2개씩 구매
        for (int i = 0; i < numCustomers; i++) {
            int purchaseQuantity = new Random().nextInt(5) + 1; // 1부터 5개 사이의 랜덤 구매 수량
            InventoryLog inventoryLog = PcController.getInstance().bread2(purchaseQuantity);
            System.out.println(inventoryLog);

        }


    }


} // ProductView 클래스 종료

