package Model;

import java.io.Serializable;

public class Items implements Serializable {
    private int itemCode;
    private Boolean available;
    private String title;
    private String author;
    private Members lendOutBy;



    public Items(int itemCode, Boolean available, String title, String author) {
        this.itemCode = itemCode;
        this.available = available;
        this.title = title;
        this.author = author;
    }
    public Members getLendOutBy() {
        return lendOutBy;
    }

    public void setLendOutBy(Members lendOutBy) {
        this.lendOutBy = lendOutBy;
    }
    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
