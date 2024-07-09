package model.dto;
 
public class BoardDto {
    private int bmo;
    private String bcontent;
    private String bdate;
    private String store_id;


    public BoardDto() {
    }

    public BoardDto(int bmo, String bcontent, String bdate, String store_id) {
        this.bmo = bmo;
        this.bcontent = bcontent;
        this.bdate = bdate;
        this.store_id = store_id;

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

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public int getBmo() {
        return bmo;
    }

    public void setBmo(int bmo) {
        this.bmo = bmo;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "bcontent='" + bcontent + '\'' +
                ", bdate='" + bdate + '\'' +
                ", store_id='" + store_id + '\'' +
                ", bmo=" + bmo +
                '}';
    }

}
