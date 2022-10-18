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

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private ImageView DashBoardImage;
    @FXML private TableView tableViewMembers;
    @FXML private TableView tableViewCollection;
    @FXML private TextField TxtItemCode;
    @FXML private TextField TxtMemberIdentifier;
    @FXML private TextField TxtReceiveItemCode;
    @FXML private Label LblLendItemSuccses;
    @FXML private Label LblLendItemError;
    @FXML private Label LblWelcome;
    @FXML private Label LblReceiveItemError;
    @FXML private Label LblReceiveItemSuccses;
    private Members currentMember;
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

    @FXML private void BtnLendItemOnAction(ActionEvent event){
        try{
            ClearMessageText();
            EmptyTextBox(TxtItemCode.getText());
            EmptyTextBox(TxtMemberIdentifier.getText());
            Items selectedItem = SelectItem(Integer.parseInt(TxtItemCode.getText()));
            CheckNull(selectedItem, "*Member does not exist");

            Members selectedMember = SelectMember();
            CheckNull(selectedMember, "*Item not found in this library");

            if(selectedItem.getAvailable().equals(true)){
                selectedMember.AddItem(selectedItem);
                selectedItem.setAvailable(false);
            }
            else{
                throw new Exception("*Item is not available now");
            }
            LblLendItemSuccses.setText("*Item is lent out successfully");
        }catch(Exception ex){
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

    private Items SelectItem(int selectedItem){
        for(Items item : listOfItems){
            if(item.getItemCode() == selectedItem){
                return item;
            }
        }
        return null;
    }

    private void CheckNull(Object object, String errorMessage) throws Exception {
        if(object == null){
            throw new Exception(errorMessage);
        }
    }

    @FXML private void BtnReceiveItemOnAction(){
        try{
            ClearMessageText();
            EmptyTextBox(TxtReceiveItemCode.getText());
            Items item = SelectItem(Integer.parseInt(TxtReceiveItemCode.getText()));

            CheckNull(item, "*Item not recognized");

            if(item.getAvailable().equals(false)){
                item.setAvailable(true);
                LblReceiveItemSuccses.setText("*Received item successfully");
            }
            else {
                throw new Exception("*Item never been lend out");
            }
        }
        catch (Exception ex){
            LblReceiveItemError.setText(ex.getMessage());
        }
    }
    public void EmptyTextBox(String textBox) throws Exception {
        if(textBox.isEmpty()){
            throw new Exception("Please, enter a value");
        }
    }
    private void ClearMessageText(){
        LblLendItemError.setText("");
        LblLendItemSuccses.setText("");
        LblReceiveItemError.setText("");
        LblReceiveItemSuccses.setText("");
    }



}
