package view;

import controller.Bcontroller;
import model.dto.BoardDto;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardView {
    private static BoardView Bview = new BoardView();
    private BoardView(){}
    public static BoardView getInstance() {
        return Bview;
    }


    Scanner scan = new Scanner(System.in);

    public void Bprinter(){
        ArrayList<BoardDto> result = Bcontroller.getInstance().Bprinter();
        System.out.println("게시물 번호\t게시물내용\t작성날짜\t작성자");
        result.forEach(dto->{
            System.out.printf("%2s\t%20d\t%10s\t%2s" , dto.getBmo(), dto.getBcontent(),dto.getBdate(),dto.getStore_id());
        });

    }
}
