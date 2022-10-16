package Database;

import Model.Members;
import Model.Items;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
private List<Members> allMembers = new ArrayList<>();
private List<Items> allItems = new ArrayList<>();

    public Database() {
        this.allMembers.add(new Members(1, "Jens", "Blijenburg", LocalDate.of(100, 2, 11), "Jens", "Jens123"));
        this.allMembers.add(new Members(2, "Serena", "Kenter", LocalDate.of(102, 8, 10), "Serena", "Kenter123"));
        this.allMembers.add(new Members(3, "Piet", "Janssen", LocalDate.of(99, 2, 2), "Piet", "Piet123"));
        this.allMembers.add(new Members(4, "Sjon", "Klaassen", LocalDate.of(2005, 1, 1), "Sjon", "Piet123"));
        this.allMembers.add(new Members(5, "Keesje", "Hes", LocalDate.of(1950, 5, 8), "Keesje", "Keesje123"));

        this.allItems.add(new Items(1, true, "Java deel 1", "Jan De Man"));
        this.allItems.add(new Items(2, true, "Java deel 2", "Jan Van Klaas"));
        this.allItems.add(new Items(3, true, "Java deel 3", "Piet De Vrouw"));
        this.allItems.add(new Items(4, true, "Java deel 4", "Hendrik Anders"));
        this.allItems.add(new Items(5, true, "Java deel 5", "Sjon Nietdom"));
    }

    public List<Members> getAllMembers() {
        return allMembers;
    }

    public List<Items> getAllItems() {
        return allItems;
    }
}
