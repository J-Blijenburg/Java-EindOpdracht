package Database;

import Model.Members;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
private List<Members> allMembers = new ArrayList<>();

    public Database() {
        this.allMembers.add(new Members(1, "Jens", "Blijenburg", LocalDate.of(100, 2, 11), "Jens", "Jens123"));
        this.allMembers.add(new Members(2, "Serena", "Kenter", LocalDate.of(102, 8, 10), "Serena", "Kenter123"));
        this.allMembers.add(new Members(3, "Piet", "Janssen", LocalDate.of(99, 2, 2), "Piet", "Piet123"));
        this.allMembers.add(new Members(4, "Sjon", "Klaassen", LocalDate.of(2005, 1, 1), "Sjon", "Piet123"));
        this.allMembers.add(new Members(5, "Keesje", "Hes", LocalDate.of(1950, 5, 8), "Keesje", "Keesje123"));
    }

    public List<Members> getAllMembers() {
        return allMembers;
    }
}
