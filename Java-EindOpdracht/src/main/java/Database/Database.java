package Database;

import Model.Items;
import Model.Members;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable{
private List<Members> allMembers = new ArrayList<>();
private List<Items> allItems = new ArrayList<>();


    public Database() throws IOException, ClassNotFoundException {
        Read();
    }

    public List<Members> getAllMembers() {
        return allMembers;
    }
    public List<Items> getAllItems() {
        return allItems;
    }


    public void Write() throws IOException {
        FileOutputStream fileMembers = new FileOutputStream("JavaEindopdrachtMembers.txt");
        ObjectOutputStream objectMembers = new ObjectOutputStream(fileMembers);
        FileOutputStream fileItems = new FileOutputStream("JavaEindopdrachtItems.txt");
        ObjectOutputStream objectItems = new ObjectOutputStream(fileItems);

        for(Members member : allMembers){
            objectMembers.writeObject(member);
        }

        for(Items item : allItems){
            objectItems.writeObject(item);
        }


        objectMembers.close();
        objectItems.close();
        fileMembers.close();
        fileItems.close();

    }

    private void Read() throws IOException, ClassNotFoundException {
        FileInputStream fileMembers = new FileInputStream("JavaEindopdrachtMembers.txt");
        ObjectInputStream objectMembers = new ObjectInputStream(fileMembers);

        FileInputStream fileItems = new FileInputStream("JavaEindopdrachtItems.txt");
        ObjectInputStream objectItems = new ObjectInputStream(fileItems);


        while(fileMembers.available() > 0){
            allMembers.add((Members) objectMembers.readObject());
        }
        while(fileItems.available() > 0){
            allItems.add((Items)objectItems.readObject());
        }

        objectMembers.close();
        objectItems.close();
        fileMembers.close();
        fileItems.close();

    }

}
