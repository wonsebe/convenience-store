package convenience-store;

import convenience-store.model.ProductDao;
import convenience-store.model.ProductDto;

import java.util.Random;

public class PcController {


    // 1. 구매
    public boolean 구매(int productDto){

        // 1. 랜덤으로 구매할 제품 선택
        int pNum = new Random().nextInt(10)+1; //  1~3
        System.out.println("제품번호 : "+pNum+" 구매 했습니다.");

        // 재고확인
        int result = 개별재고확인(  pNum );
        System.out.println( result );

        if( result <= 0) {
            System.out.println("재고 부족 ");
            return false;
        }

        // 2. 랜덤으로 구매수량
        int pCount = new Random().nextInt(11); // 0 ~ 10( 선택한 제품의 재고 수량 )
        System.out.println("구매수량 : "+ pCount +"입니다.");

        Dao.getInstance().구매( );

        return true;
    }

    public int 개별재고확인( int pNum ){
        return Dao.getInstance().개별재고확인( pNum );
    }

}
