package controller;

import model.dao.BoardDao;
import model.dto.BoardDto;

import java.util.ArrayList;

public class Bcontroller {

    private static final Bcontroller bcontrol = new Bcontroller();

    private Bcontroller() {
    }

    public static Bcontroller getInstance() {
        return bcontrol;
    }


    public ArrayList<BoardDto> Bprinter() {
        return BoardDao.getInstance().Bprinter();
    }


}   // Bcontroller end
