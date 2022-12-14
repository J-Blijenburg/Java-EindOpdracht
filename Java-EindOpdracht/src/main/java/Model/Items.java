package Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Items implements Serializable {
    private int itemCode;
    private Boolean available;
    private String title;
    private String author;
    private Members lendOutBy;
    private LocalDate lendOutDate;


    public Items(int itemCode, Boolean available, String title, String author) {
        this.itemCode = itemCode;
        this.available = available;
        this.title = title;
        this.author = author;
    }

    public LocalDate getLendOutDate() {
        return lendOutDate;
    }

    public void setLendOutDate(LocalDate lendOutDate) {
        this.lendOutDate = lendOutDate;
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
