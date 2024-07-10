package controller;

import model.dao.InventoryDao;
import model.dao.ProductDao;
import model.dao.SalesDao;
import model.dao.StoreDao;
import model.dto.InventoryLog;
import model.dto.Products;
import view.ProductView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// 편의점 시뮬레이션 게임의 핵심 로직을 관리하는 컨트롤러 클래스
// 싱글톤 패턴을 사용해 구현됨
public class PcController {
    // 싱글톤 패턴을 위한 자기 자신의 인스턴스
    private static final PcController pControl = new PcController();
    private static final int RENT_AMOUNT = 300000; // 월세 금액
    private static final int RENT_INTERVAL = 5; // 월세 납부 주기 (턴)
    private final GameSaveDao gameSaveDao = GameSaveDao.getInstance();
    private int productTypeCount; // 등록된 상품 종류의 수를 저장
    private int lastTurnTotalSales; // 마지막 턴의 총 매출액을 저장
    private int turn;
    private int storeBalance; // 편의점 현금

    // private 생성자. 외부에서 인스턴스 생성 방지
    // 초기화 시 등록된 상품 종류의 수 조회
    private PcController() {
        // 초기화 시 등록된 상품 종류의 수 조회
        this.productTypeCount = ProductDao.getInstance().getProductTypeCount();
        this.lastTurnTotalSales = 0;
        this.storeBalance = StoreDao.getInstance().getBalance();
    }

    // 싱글톤 인스턴스 반환 메서드
    public static PcController getInstance() {
        return pControl;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getCurrentLoginId() {
        return this.currentLoginId;
    }

    public void setCurrentLoginId(String loginId) {
        this.currentLoginId = loginId;
    }

    // saveGameState 메서드
    public void saveGameState() {
        GameStateDto gameState = new GameStateDto(
                turn,
                getCurrentInventoryLogs(),
                storeBalance,
                getCurrentBoardNotices(),
                lastTurnTotalSales
        );
        gameSaveDao.saveGame(currentLoginId, gameState);
    }

    // loadGameState 메서드
    public void loadGameState() {
        GameStateDto gameState = gameSaveDao.loadGame(currentLoginId);
        if (gameState != null) {
            this.turn = gameState.getCurrentTurn();
            this.storeBalance = gameState.getStoreBalance();
            this.lastTurnTotalSales = gameState.getLastTurnTotalSales();
            updateInventoryFromLogs(gameState.getInventoryLogs());
            updateBoardNotices(gameState.getBoardNotices());
            if (gameState.getProducts() != null) {
                updateProducts(gameState.getProducts());
            }

            // 재고 로그 복원
            List<InventoryLog> inventoryLogs = gameState.getInventoryLogs();
            // inventoryLogs를 사용하여 현재 재고 상태를 업데이트
            updateInventoryFromLogs(inventoryLogs);

            // 공지사항 복원
            List<BoardDto> boardNotices = gameState.getBoardNotices();
            updateBoardNotices(boardNotices);

            // 상품 정보 복원 (만약 상품 정보가 GameState에 포함되어 있다면)
            if (gameState.getProducts() != null) {
                updateProducts(gameState.getProducts());
            }

            // 마지막 턴 매출액 복원
            this.lastTurnTotalSales = gameState.getLastTurnTotalSales();

            // 등록된 상품 종류 수 복원
            this.productTypeCount = gameState.getProductTypeCount();

            // 기타 필요한 게임 상태 변수들 복원
            // 예: 이벤트 상태, 특별 조건 등
            restoreAdditionalGameStates(gameState);

            System.out.println("게임 상태가 성공적으로 로드되었습니다. 현재 턴: " + this.turn);
        } else {
            System.out.println("저장된 게임 상태가 없거나 로드에 실패했습니다. 새 게임을 시작합니다.");
            initializeNewGame();
        }
    }

    private void restoreAdditionalGameStates(GameStateDto gameState) {
        // 게임 상태 복원 로직
        // 이벤트 상태, 특별 조건 등
    }

    private List<Products> getCurrentProducts() {
        // 현재 상품 목록을 가져오는 로직
        // ...
        return null;
    }

    private void updateProducts(List<Products> products) {
        // 로드된 상품 정보로 현재 상품 상태를 업데이트하는 로직
        // ...
    }

    // 1 - 재고 구매 메서드
    public String supplyRestock(int pId, int quantity, int turn) {
        // 구매할 제품의 소매가를 가져온다
        int retailPrice = ProductDao.getInstance().getProductPrice(pId);
        // 도매가를 계산한다. (소매가의 60%)
        int wholeSalePrice = (int) (retailPrice * 0.6);
        // 총 구매가격을 계산한다
        int orderFunds = wholeSalePrice * quantity;

        // 편의점 자금이 부족하면 구매 불가를 출력한다
        if (orderFunds >= this.storeBalance) {
            return ColorUtil.getColor("RED") + "구매할 자금이 부족합니다." + ColorUtil.getColor("RESET");
        } else {
            // 자금이 충분하면 StoreDao에 전달해 편의점 자금 상태를 변경한다
            int newBalance = this.storeBalance - orderFunds;
            this.storeBalance -= orderFunds;
            boolean updateSuccess = StoreDao.getInstance().updateBalance(newBalance, turn);
            if (updateSuccess) {
                this.storeBalance = newBalance; // 잔고 변경
                // InventoryDao 에서 상품의 수량도 변경한다
                InventoryDao.getInstance().supplyRestock(pId, quantity, turn);
                return ColorUtil.getColor("GREEN") + "구매완료!" + ColorUtil.getColor("RESET");
            } else {
                return ColorUtil.getColor("RED") + "잔고 업데이트 실패. 다시 시도해주세요." + ColorUtil.getColor("RESET");
            }
        }
    } // 1 - 재고 구매 메서드 end

    // 2 - 재고 확인 메서드
    public int checkInventory(int productId) {
        return InventoryDao.getInstance().checkInventory(productId);
    } // 2 - 재고 확인 메서드 end

    // 3 - 상품 추가 메서드
    public boolean addProduct(Products product) {
        boolean result = ProductDao.getInstance().add(product);
        if (result) {
            // 상품이 성공적으로 추가되면 상품 종류 수를 갱신
            updateProductTypeCount();
        }
        return result; // 상품 추가 성공여부 반환
    } // 3 - 상품 추가 메서드 end

    // 4 - 상품 수정 메서드
    public boolean updateProduct(Products product) {
        return ProductDao.getInstance().pUpdate(product);
    } // 4 - 상품 수정 메서드 end

    // 5 - 재고 삭제 메서드
    public boolean pDelete(int productId) {
        return InventoryDao.getInstance().pdelete(productId);
    } // 5 - 재고 삭제 메서드 end

    // 6 - 물품 확인
    public ArrayList<Products> pPrint() {

        ArrayList<Products> result = InventoryDao.getInstance().pPrint();
        // 물품확인 리스트내 있는 모든 제품수량에 대한 구매 혹은 판매에 대한 누적 값 구하기
        for (int i = 0; i < result.size(); i++) {  // result 0부터 result 마지막까지 1씩 플러스 한다.
            Products products = result.get(i);      // result에 있는 i번째의 인덱스 값을 products에 대입한다.
            // products : 0인덱스[ Products = 첫번째 레코드 ] 1인덱스 [ Products = 두번째 레코드 ] 2인덱스[ Products = 세번째 레코드 ] ~~~
            // - 제품수량에 대한 구매 혹은 판매에 대한 누적 값 구하기
            int stock = InventoryDao.getInstance().stock(products.getProductId()); // i번째 레코드의 제품번호의 재고 수량 계산
            // i번째 레코드의 제품번호 객체의 재고 수량 대입
            products.setStock(stock);
        }
        return result;
    } // 6 - 물품 확인 메서드 end

    // 99.1 - 손님 방문 메서드
    // 랜덤한 수의 고객이 랜덤한 상품을 랜덤 수량으로 구매하려 시도
    public ArrayList<InventoryLog> purchase(int turn) {
        ArrayList<InventoryLog> logs = new ArrayList<>();
        // 3~15명의 랜덤한 고객 수 생성
        int customerCount = new Random().nextInt(13) + 3;

        for (int i = 0; i < customerCount; i++) {
            // 랜덤한 상품 ID 선택
            int productId = new Random().nextInt(productTypeCount) + 1;
            // 1~5개의 랜덤한 구매 수량 생성
            int buyCount = new Random().nextInt(5) + 1;

            int purchaseQuantity = new Random().nextInt(2) + 1; // 1부터 2개 사이의 랜덤 구매 수량

            int productCount = InventoryDao.getInstance().checkInventory(productId);
            // 확인용 콘솔
            // System.out.println("상품 ID " + productId + " 구매 시도: 요청 수량 " + buyCount + ", 현재 재고 " + productCount); // 디버깅을 위한 출력

            if (productCount >= buyCount) {
                InventoryLog log = InventoryDao.getInstance().purchase(productId, buyCount, turn);
                if (log != null) {
                    logs.add(log);
                    // 확인용 콘솔
                    // System.out.println("구매 성공: 상품 ID " + productId + ", 수량 " + buyCount); // 디버깅을 위한 출력
                }
            } else {
                InventoryLog log = new InventoryLog();
                log.setProductId(productId);
                log.setQuantity(0);
                log.setDescription("재고 부족으로 구매 실패");
                logs.add(log);
                // 확인용 콘솔
                // System.out.println("구매 실패: 상품 ID " + productId + ", 요청 수량 " + buyCount + ", 현재 재고 " + productCount); // 디버깅을 위한 출력
            }
        }
        return logs;
    } // 99.1 - 손님 방문 메서드 end

    // 상품 이름 조회 메서드
    public String getProductName(int productId) {
        return ProductDao.getInstance().getProductName(productId);
    } // 상품 이름 조회 메서드 end

    // 등록된 상품 종류 수 반환 메서드
    public int getProductTypeCount() {
        return this.productTypeCount;
    } // 등록된 상품 종류 수 반환 메서드 end

    // 등록된 상품 종류 수 갱신 메서드
    public void updateProductTypeCount() {
        this.productTypeCount = ProductDao.getInstance().getProductTypeCount();
    } // 등록된 상품 종류 수 갱신 메서드 end

    // 구매 처리, 총 매출 계산, 매출 저장, 잔고 저장
    public void processPurchaseAndSales(int turn) {
        ArrayList<InventoryLog> logs = purchase(turn);
        int totalSales = SalesDao.getInstance().calculateTotalSales(logs);
        SalesDao.getInstance().saveSales(turn, totalSales);
        this.lastTurnTotalSales = totalSales;

        // 잔고 업데이트
        this.storeBalance += totalSales;
        StoreDao.getInstance().updateBalance(this.storeBalance, turn);
    } // 구매 처리, 총 매출 계산, 매출 저장, 잔고 저장 end

    // 마지막 턴 매출액 가져오기
    public int getLastTurnTotalSales() {
        return this.lastTurnTotalSales;
    }

    // 월세 내는 메서드
    public boolean deductRent(int turn) {
        if (turn % RENT_INTERVAL == 0) {
            if (this.storeBalance >= RENT_AMOUNT) {
                this.storeBalance -= RENT_AMOUNT;
                StoreDao.getInstance().updateBalance(this.storeBalance, turn);
                System.out.println("=========================================================");
                System.out.println(ColorUtil.getColor("YELLOW") + "월세 " + RENT_AMOUNT + "원이 차감되었습니다." + ColorUtil.getColor("RESET"));
                System.out.println("=========================================================");
                return true;
            } else {
                System.out.println(ColorUtil.getColor("RED") + "월세를 낼 돈이 부족합니다. 게임 오버!" + ColorUtil.getColor("RESET"));
                return false;
            }
        }
        return true;
    }

    // 편의점 잔고 가져오기
    public int getStoreBalance() {
        return this.storeBalance;
    }

    // 편의점 잔고 갱신
    public void updateStoreBalance(int amount) {
        this.storeBalance = amount;
    }

    public void inrush() {
        Random random = new Random();
        // 랜덤하게 상품 선택

        int productId = random.nextInt(productTypeCount) + 1;
        // 랜덤하게 감소할 수량 선택
        int quantity = random.nextInt(2) + 1; //1부터 3까지 수량을 랜덤으로 가져감


        //이름과 수량을 다오로 보냄
        InventoryDao.getInstance().inrush(productId, quantity);

    }

    //편의점 포켓몬빵 입고
    public void bread1(int turn){
InventoryDao.getInstance().supplyRestock(30,120,turn);
    }
    public InventoryLog bread2(int turn,int purchaseQuantity){

        return InventoryDao.getInstance().purchase(30,purchaseQuantity,turn);
    }


} // PcController 클래스 end





































