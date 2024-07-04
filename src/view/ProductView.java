package view;

import controller.PcController;
import model.dto.Products;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductView {
    private static ProductView pView = new ProductView();
    private ProductView(){}
    public static ProductView getInstance(){return  pView;}

    // 사용자 입력을 받기 위한 Scanner 객체 생성
    Scanner scan = new Scanner(System.in);

    // 현재 턴을 저장하는 변수 초기화
    int turn = 1;

    //멤버변수 : 입력객체
    public  void index(){
        PcController.getInstance().purchase(turn);
        // 무한 루프를 통해 게임 진행
        while (true) {
            // 모든 제품에 대해 재고 확인
            for (int i = 1; i <= 30; i++) {
                System.out.print(" 제품번호: " + i);
                int inventory = PcController.getInstance().checkInventory(i);  // 개별 제품의 재고 확인
                System.out.println("       재고 = " + inventory);
            } // for문 끝

            // 사용자에게 행동 선택을 요청
            System.out.print("마이 턴 : 1.재고 구매 2.재고 확인 3. 재고 수정 4. 재고 삭제  5. 턴넘기기 : ");
            int choice = scan.nextInt();

            if (choice == 1) {
                // 사용자가 재고 구매를 선택한 경우
                System.out.print("컴퓨터 턴 ");
                PcController.getInstance().purchase(turn);  // 현재 턴을 인자로 하여 구매 메서드 호출
                turn++;  // 턴 수 증가
            }
            else if (choice == 2) {pPrint();
                // 사용자가 재고 확인을 선택한 경우



            }
            else if (choice ==3) { pUpdate();     }

            else if (choice ==4) {      }
            else if (choice ==5){}
            else {// 잘못된 선택을 한 경우 경고 메시지 출력
                System.out.println("잘못된 선택입니다. 다시 시도해주세요.");}
            } // if문 끝
        } // while 끝

    // 상품조회
    public void pPrint(){
        ArrayList<Products> result = PcController.getInstance().pPrint();
        System.out.println("제품번호\t 제품명\t\t 제품가격");
        result.forEach(dto->{
            System.out.printf("%d\t%s\t\t%s \n", dto.getProductId(), dto.getName() ,dto.getPrice());
        }
        );
    }

    // 물품가격 수정
    public void pUpdate(){
        System.out.println("변경하실 물품번호 :"); int productId = scan.nextInt();
        System.out.println("변경하실 금액 :");    int price = scan.nextInt();

        Products products = new Products();
        products.setProductId(productId);
        products.setPrice(price);

        boolean result = PcController.getInstance().pUpdate(products);
        if (result){
            System.out.println("수정성공");
        }else {
            System.out.println("수정실패");
        }




    }



}



