package controller;

import model.dao.BoardDao;
import model.dto.BoardDto;

import java.util.ArrayList;

public class Bcontroller {
    // 싱글톤
    private static Bcontroller bcontrol = new Bcontroller();
    private  Bcontroller(){}
    public static Bcontroller getInstance(){return bcontrol;}

    // 1. 전체출력
    public ArrayList<BoardDto> Bprinter(){
        return BoardDao.getInstance().Bprinter();
    }

    // 2. 게시물 쓰기
    public boolean Bwrite(String bcontent ){
        BoardDao.getInstance().Bwrite(bcontent );
        return true;
    }


}   // Bcontroller end
