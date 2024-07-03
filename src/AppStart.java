import controller.PcController;

import java.util.Scanner;

public class AppStart {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int 턴수 = 1;
        while (true) {
            for (int i = 1; i <= 10; i++) {
                System.out.print(" 제품번호 : " + i);
                int result = new PcController().개별재고확인(i);
                System.out.println("       재고 = " + result);
            }
            System.out.print("마이 턴 : 1.재고 구매 2.재고 확인 : ");
            int ch = scan.nextInt();
            System.out.print("컴퓨터 턴 ");

            new PcController().구매(턴수);
            턴수++;
        }
    }
}
