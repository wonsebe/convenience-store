package view;

import controller.Bcontroller;
import model.dto.BoardDto;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardView {
    private static final BoardView Bview = new BoardView();
    Scanner scan = new Scanner(System.in);

    private BoardView() {
    }

    public static BoardView getInstance() {
        return Bview;
    }

    public void Bprinter() {
        ArrayList<BoardDto> result = Bcontroller.getInstance().Bprinter();
        System.out.println("게시물 번호\t\t게시물내용\\t\t\t작성자\n");
        result.forEach(dto->{
            System.out.printf("\t%2d\t%15s\t%2s\n" , dto.getBmo(), dto.getBcontent(),dto.getStore_id());
        });
    }

    // 게시물 쓰기
    public void Bwrite(){
        System.out.println("게시글 작성"); String bcontent = scan.nextLine();

        boolean result = Bcontroller.getInstance().Bwrite(bcontent);
        if (result){
            System.out.println("게시물 등록 성공");
        }else {
            System.out.println("게시물 등록 실패");
        }
    }





}   // BoardView end
