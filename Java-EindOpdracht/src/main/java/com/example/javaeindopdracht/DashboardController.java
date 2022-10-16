package com.example.javaeindopdracht;

import Database.Database;
import Model.Items;
import Model.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private ImageView DashBoardImage;
    @FXML
    private Label LblWelcome;

    private Members currentMember;
    @FXML
    private TableView tableViewMembers;

    @FXML private TableView tableViewCollection;
    @FXML private Label LblLendItemError;
    @FXML private TextField TxtItemCode;
    @FXML private TextField TxtMemberIdentifier;

    private ObservableList<Members> listOfMembers;
    private ObservableList<Items> listOfItems;

    private Database db = new Database();

    public DashboardController(Members member){
       this.currentMember = member;
        this.listOfMembers = FXCollections.observableList(this.db.getAllMembers());
        this.listOfItems = FXCollections.observableList(this.db.getAllItems());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LblWelcome.setText("Welcome " + currentMember.getFirstName());
        tableViewMembers.setItems(listOfMembers);
        tableViewCollection.setItems(listOfItems);

    }

    @FXML public void BtnLendItemOnAction(ActionEvent event){


        try{
            Items selectedItem = SelectItem();
            if(selectedItem.equals(null)){
               throw new Exception("Member is not existing, Try another one.") ;
            }

            Members selectedMember = SelectMember();
            if(selectedMember.equals(null)){
                throw new Exception("Item not found in this library");
            }
            selectedMember.AddItem(selectedItem);
            LblLendItemError.setText(String.valueOf(selectedMember.getItemsLend().size()));

        }catch(Exception ex){
            LblLendItemError.setText("");
            LblLendItemError.setText(ex.getMessage());
        }

    }
    private Members SelectMember(){
        for(Members member : listOfMembers){
            if(member.getId() == Integer.parseInt(TxtMemberIdentifier.getText())){

                return member;
            }
        }
        return null;
    }

    private Items SelectItem(){
        for(Items item : listOfItems){
            if(item.getItemCode() == Integer.parseInt(TxtItemCode.getText())){
                return item;
            }
        }
        return null;
    }
}
