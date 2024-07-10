package model.dto;
 
public class BoardDto {
    private int bmo;
    private String bcontent;
    private int bdate;
    private String store_id;


    public BoardDto() {
    }

    public BoardDto(int bmo, String bcontent, int bdate, String store_id) {
        this.bmo = bmo;
        this.bcontent = bcontent;
        this.bdate = bdate;
        this.store_id = store_id;

    }
    public BoardDto(int bmo, String bcontent, String store_id) {
        this.bmo = bmo;
        this.bcontent = bcontent;
        this.store_id = store_id;

    }

    public String getBcontent() {
        return bcontent;
    }

    public void setBcontent(String bcontent) {
        this.bcontent = bcontent;
    }

    public int getBdate() {
        return bdate;
    }

    public int setBdate(int bdate) {return bdate;  }

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
