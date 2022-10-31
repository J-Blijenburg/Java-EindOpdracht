package com.example.javaeindopdracht;

import Model.Items;
import Model.Members;
import com.example.javaeindopdracht.Exception.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LendingReceivingController implements Initializable {
    @FXML private TextField txtReceiveItemCode;
    @FXML private Label lblWelcome;
    @FXML private Label lblReceiveItemSuccses;
    @FXML private Label lblReceiveItemError;
    @FXML private TextField txtItemCode;
    @FXML private TextField txtMemberIdentifier;
    @FXML private Label lblPayFine;
    @FXML private Label lblTotalPayFine;
    @FXML private Button btnReceiveItem;
    @FXML private Button btnPayFine;
    private Boolean hasToPayFine;
    private final ObservableList<Items> listOfItems;
    private final ObservableList<Members> listOfMembers;

    private final Members currentMember;
    private int totalDaysToLate;
    private Items itemToPayOff;


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
                throw new ItemNotLendOutException();
            }


            checkLendOutBy(item);

        }
        catch (Exception ex){
            lblReceiveItemError.setText(ex.getMessage());
        }
    }

    //Check if both Textboxes are filled. Assign the selected item to the selected member
    @FXML public void btnLendItemOnAction(ActionEvent event){
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
                throw new ItemNotAvailableException();
            }
            lblReceiveItemSuccses.setText("Item " + selectedItem.getTitle() + " is lent out by " +selectedMember.getFirstName() +  " successfully on " + selectedItem.getLendOutDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }catch(Exception ex){
            lblReceiveItemError.setText(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblWelcome.setText("Welcome " + currentMember.getFirstName() + " " + currentMember.getLastName());
    }
    //set the current text of the label to not
    private void clearCurrentTextOfLabel(){
        lblPayFine.setText(null);
        lblTotalPayFine.setText(null);
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
    private void checkLendOutBy(Items item) throws DidNotLendException {
        if(item.getLendOutBy().getId() == currentMember.getId()){
            checkDeadLine(item);
            if(hasToPayFine){
                itemToPayOff = item;
                itemToLate();
            }
            else{
                normalItemSettings(item);
            }
        }
        else{
            throw new DidNotLendException();
        }
    }
    private void checkDeadLine(Items item){
        LocalDate deadLine = item.getLendOutDate().plusDays(21);
        Period periodeBetween = Period.between(LocalDate.now(), deadLine);
        totalDaysToLate = Math.abs(periodeBetween.getDays());
        if(LocalDate.now().isBefore(deadLine)){
            hasToPayFine = false;
            lblReceiveItemSuccses.setText("Received item successfully");
        }
        else{
            hasToPayFine = true;
        }
    }
    private void itemToLate(){
        btnReceiveItem.setDisable(true);
        btnPayFine.setDisable(false);
        btnPayFine.setOpacity(1);
        lblPayFine.setText("Item is " + totalDaysToLate + " days to late!");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        lblTotalPayFine.setText("Total Fine: € " + decimalFormat.format(calculateFine()));
    }

    private double calculateFine(){
        double totalFine = 0;
        for(int i = 0; i < totalDaysToLate; i++){
            totalFine += 0.10;
        }

        return totalFine;
    }

    //set item settings back to normal
    private void normalItemSettings(Items item){
        item.setAvailable(true);
        item.setLendOutBy(null);
        item.setLendOutDate(null);
    }

    @FXML private void btnPayFineOnAction(){
        removePayFineButton();
        normalItemSettings(itemToPayOff);
        lblPayFine.setText("Payment complete and received item successfully");
        lblTotalPayFine.setText("");
        btnReceiveItem.setDisable(false);
    }

    private void removePayFineButton(){
        btnPayFine.setOpacity(0);
        btnPayFine.setDisable(true);
    }
}
