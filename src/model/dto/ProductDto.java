package model.dto;

public class ProductDto {
    private int game_date;
    private  int product_id;
    private  String ProductName;
    private  int pNum;
    private  int pCount;
    private  int quantity;
    private   String description;      //제품내용

    public ProductDto(int game_date, int product_id, String productName, int pNum, int pCount, int quantity, String description) {
        this.game_date = game_date;
        this.product_id = product_id;
        ProductName = productName;
        this.pNum = pNum;
        this.pCount = pCount;
        this.quantity = quantity;
        this.description = description;
    }

    public int getGame_date() {
        return game_date;
    }

    public void setGame_date(int game_date) {
        this.game_date = game_date;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public int getpCount() {
        return pCount;
    }

    public void setpCount(int pCount) {
        this.pCount = pCount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "game_date=" + game_date +
                ", product_id=" + product_id +
                ", ProductName='" + ProductName + '\'' +
                ", pNum=" + pNum +
                ", pCount=" + pCount +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
