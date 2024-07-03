
import controller.ProductController;
import project.model.ProductDto;

import java.util.Random;
import java.util.Scanner;

public class AppStart {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        int game_date = 1;
        while ( true){

            for( int i = 1 ; i<=10 ; i++ ){
                System.out.print(" 제품번호 : "+ i ); //제품번호가 i 번째가 되면서 계속 늘어남

                int result = new ProductController().개별재고확인( i ); // PcController가 재고 개별 번호를 가져와서 result변수에 대입
                System.out.println("       재고 = " + result); //재고 확인 출력
            }

            System.out.print("마이 턴 : 1.재고 구매 2.재고 확인 : ");
            int ch = scan.nextInt();
            System.out.print("컴퓨터 턴 ");

            new ProductController().구매(game_date );



            game_date++;
        }
    }
}
