package Model;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Members implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String userName;
    private String passWord;
    private List<Items> itemsLend;

    public Members(int id, String firstName, String lastName, LocalDate birthDate, String userName, String passWord) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.userName = userName;
        this.passWord = passWord;
        this.itemsLend = new ArrayList<>();
    }

    public List<Items> getItemsLend() {
        return itemsLend;
    }

    public void setItemsLend(List<Items> itemsLend) {
        this.itemsLend = itemsLend;
    }

    public void AddItem(Items item){
        itemsLend.add(item);
    }

    public void RemoveItem(Items item){
        itemsLend.remove(item);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }




}
