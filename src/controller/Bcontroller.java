package controller;

import model.dao.BoardDao;
import model.dto.BoardDto;

import java.util.ArrayList;

public class Bcontroller {
    // 싱글톤
    private static final Bcontroller bcontrol = new Bcontroller();

    private Bcontroller() {
    }

    public static Bcontroller getInstance() {
        return bcontrol;
    }

    // 1. 전체출력
    public ArrayList<BoardDto> Bprinter() {
        return BoardDao.getInstance().Bprinter();
    }

    // 2. 글쓰기
    public boolean addNotice(String content, String authorLoginId) {
        return BoardDao.getInstance().addNotice(content, authorLoginId);
    }

    public ArrayList<BoardDto> getAllNotices() {
        return BoardDao.getInstance().getAllNotices();
    }


}   // Bcontroller end
