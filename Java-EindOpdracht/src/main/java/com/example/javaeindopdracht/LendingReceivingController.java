package com.example.javaeindopdracht;

import Model.Items;
import Model.Members;
import com.example.javaeindopdracht.Exception.ItemNotFoundException;
import com.example.javaeindopdracht.Exception.MemberNotFoundException;
import com.example.javaeindopdracht.Exception.ValidNumberException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LendingReceivingController implements Initializable {
    @FXML private TextField txtReceiveItemCode;
    @FXML private Label LblWelcome;
    @FXML private Label lblReceiveItemSuccses;
    @FXML private Label lblReceiveItemError;
    @FXML private TextField txtItemCode;
    @FXML private TextField txtMemberIdentifier;
    private ObservableList<Items> listOfItems;
    private ObservableList<Members> listOfMembers;

    private Members currentMember;
    private int dateCheck;
    private final int deadLine = 21;
    private final int totalDaysToLate = Math.abs(dateCheck - deadLine);

    public LendingReceivingController(ObservableList<Items> listOfItems, ObservableList<Members> listOfMembers, Members currentMember) {
        this.listOfItems = listOfItems;
        this.listOfMembers = listOfMembers;
        this.currentMember = currentMember;
    }

    //Receive the selected item and check if the item is lent out by the current member
    @FXML public void btnReceiveItemOnAction(ActionEvent event) {
        try{
            clearCurrentTextOfLabel();
            Items item = SelectItem(checkForInt(txtReceiveItemCode.getText()));

            if(item.getAvailable()){
                throw new Exception("Item is not lend out");
            }

            dateCheck = LocalDate.now().getDayOfYear() - item.getLendOutDate().getDayOfYear();
            checkLendOutBy(item);

        }
        catch (Exception ex){
            lblReceiveItemError.setText(ex.getMessage());
        }
    }

    //Check if both Textboxes are filled. Assign the selected item to the selected member
    @FXML public void btnLendItemOnAction(ActionEvent event) {
        try{
            clearCurrentTextOfLabel();
            Items selectedItem = SelectItem(checkForInt(txtItemCode.getText()));

            Members selectedMember = SelectMember(checkForInt(txtMemberIdentifier.getText()));

            if(selectedItem.getAvailable().equals(true)){
                selectedItem.setLendOutDate(LocalDate.now());
                selectedItem.setLendOutBy(selectedMember);
                selectedItem.setAvailable(false);
            }
            else{
                throw new Exception("Item is not available now");
            }
            lblReceiveItemSuccses.setText("Item " + selectedItem.getTitle() + " is lent out by " +selectedMember.getFirstName() +  " successfully on " + selectedItem.getLendOutDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }catch(Exception ex){
            lblReceiveItemError.setText(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LblWelcome.setText("Welcome " + currentMember.getFirstName() + " " + currentMember.getLastName());
    }
    //set the current text of the label to not
    private void clearCurrentTextOfLabel(){
        lblReceiveItemSuccses.setText(null);
        lblReceiveItemError.setText(null);
    }

    private Integer checkForInt(String text) throws ValidNumberException {
        try{
            return Integer.parseInt(text);
        }catch (NumberFormatException ex){
            throw new ValidNumberException();
        }
    }
    //Goes through every item to search the item with the correct item code
    private Items SelectItem(int selectedItem) throws ItemNotFoundException {
        for(Items item : listOfItems){
            if(item.getItemCode() == selectedItem){
                return item;
            }
        }
        throw new ItemNotFoundException();
    }

    //Goes through every member to search the member with the correct member id
    private Members SelectMember(int selectedMember) throws MemberNotFoundException{
        for(Members member : listOfMembers){
            if(member.getId() == selectedMember){
                return member;
            }
        }
        throw new MemberNotFoundException();
    }


    //check if the member actually lend the item
    private void checkLendOutBy(Items item) throws Exception {
        if(item.getLendOutBy().getId() == currentMember.getId()){
            checkDeadLine();
            normalItemSettings(item);
        }
        else{
            throw new Exception("Member didn't lend this item");
        }
    }
    private void checkDeadLine(){
        if(dateCheck < deadLine){
            lblReceiveItemSuccses.setText("Received item successfully");
        }
        else{
            lblReceiveItemError.setText("Item is " + totalDaysToLate + " days to late!");
        }
    }

    //set item settings back to normal
    private void normalItemSettings(Items item){
        item.setAvailable(true);
        item.setLendOutBy(null);
        item.setLendOutDate(null);
    }
}
