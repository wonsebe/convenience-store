package model.dto;

public class BoardDto {
    private int bmo;
    private String bcontent;
    private String bdate;
    private int store_id;

    public BoardDto(int bmo, String bcontent, String bdate, int store_id) {
        this.bmo = bmo;
        this.bcontent = bcontent;
        this.bdate = bdate;
        this.store_id = store_id;
    }

    // Getter and Setter methods
    public int getBmo() {
        return bmo;
    }

    public void setBmo(int bmo) {
        this.bmo = bmo;
    }

    public String getBcontent() {
        return bcontent;
    }

    public void setBcontent(String bcontent) {
        this.bcontent = bcontent;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }
}
