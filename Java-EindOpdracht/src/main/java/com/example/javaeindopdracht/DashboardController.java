package com.example.javaeindopdracht;

import Database.Database;
import Model.Items;
import Model.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private ImageView DashBoardImage;
    @FXML private TableView tableViewMembers;
    @FXML private TableView tableViewCollection;
    @FXML private TextField TxtItemCode;
    @FXML private TextField TxtMemberIdentifier;
    @FXML private TextField TxtReceiveItemCode;
    @FXML private TextField TxtAddMemberFirstName;
    @FXML private TextField TxtAddMemberLastName;
    @FXML private TextField TxtAddItemsTitle;
    @FXML private TextField TxtAddItemsAuthor;
    @FXML private TextField TxtEditItemTitle;
    @FXML private TextField TxtEditItemsAuthor;
    @FXML private TextField TxtEditMemberFirstName;
    @FXML private TextField TxtEditMemberLastName;

    @FXML private Label LblLendItemSuccses;
    @FXML private Label LblLendItemError;
    @FXML private Label LblWelcome;
    @FXML private Label LblReceiveItemError;
    @FXML private Label LblReceiveItemSuccses;
    @FXML private Label LblEditMember;

    @FXML private Label LblEditItems;

    @FXML private VBox VboxMembers;

    @FXML private VBox VboxAddNewMembers;
    @FXML private VBox VboxEditMembers;

    @FXML private VBox VboxCollection;

    @FXML private VBox VboxAddNewItem;

    @FXML private VBox VboxEditItems;
    @FXML private DatePicker DataPickerAddNewMember;
    @FXML private DatePicker DataPickerEditMember;




    private final Members currentMember;
    private final ObservableList<Members> listOfMembers;
    private final ObservableList<Items> listOfItems;
    private final Database database;

    public DashboardController(Members member, Database database){
       this.currentMember = member;
       this.database = database;
        this.listOfMembers = FXCollections.observableList(this.database.getAllMembers());
        this.listOfItems = FXCollections.observableList(this.database.getAllItems());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LblWelcome.setText("Welcome " + currentMember.getFirstName());
        tableViewMembers.setItems(listOfMembers);
        tableViewCollection.setItems(listOfItems);

    }

    private void EditTableViewCollection(){
        //tableViewCollection.setEditable(true);
        //TableColumn tableColumn = (TableColumn) tableViewCollection.getColumns();
       // tableColumn.setCellValueFactory(cellDataFeatures -> {
         //   boolean available = cellDataFeatures.GetValue
        //});


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
                selectedItem.setAvailable(false);
                currentMember.AddItem(selectedItem);
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

            if(currentMember.getItemsLend().equals(item)){
                if(item.getAvailable().equals(false)){
                    item.setAvailable(true);
                    LblReceiveItemSuccses.setText("*Received item successfully");
                }
                else {
                    throw new Exception("*Item never been lend out");
                }
            }
            else{
                throw new Exception("Member didn't lend this item");
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

    @FXML private void BtnAddMemberOnAction(){
        VboxMembers.setDisable(true);
        VboxMembers.setOpacity(0);
        VboxAddNewMembers.setDisable(false);
        VboxAddNewMembers.setOpacity(1);
        VboxEditMembers.setDisable(true);
        VboxEditMembers.setOpacity(0);

    }
    @FXML private void BtnAddMemberConfirm(){
        listOfMembers.add(new Members(listOfMembers.size() + 1,TxtAddMemberFirstName.getText(), TxtAddMemberLastName.getText(), LocalDate.of(DataPickerAddNewMember.getValue().getYear(), DataPickerAddNewMember.getValue().getMonth(), DataPickerAddNewMember.getValue().getDayOfMonth()) , TxtAddMemberFirstName.getText(), TxtAddMemberLastName +  "123"));
    }

    @FXML private void BtnCancelNewMember(){
        VboxMembers.setDisable(false);
        VboxMembers.setOpacity(1);
        VboxAddNewMembers.setDisable(true);
        VboxAddNewMembers.setOpacity(0);
        VboxEditMembers.setDisable(true);
        VboxEditMembers.setOpacity(0);
    }

    @FXML private void BtnAddItemOnAction(){
        VboxCollection.setDisable(true);
        VboxCollection.setOpacity(0);
        VboxAddNewItem.setDisable(false);
        VboxAddNewItem.setOpacity(1);
    }

    @FXML private void BtnAddItemConfirm(){
        listOfItems.add(new Items(listOfItems.size() + 1, true, TxtAddItemsTitle.getText(),TxtAddItemsAuthor.getText()));
    }

    @FXML private void BtnCancelNewCancel(){
        VboxCollection.setDisable(false);
        VboxCollection.setOpacity(1);
        VboxAddNewItem.setDisable(true);
        VboxAddNewItem.setOpacity(0);
    }
    @FXML private void BtnDeleteMember(){

        listOfMembers.remove(tableViewMembers.getSelectionModel().getSelectedItem());
        tableViewMembers.refresh();
    }

    @FXML private void BtnDeleteItem(){
        listOfItems.remove(tableViewCollection.getSelectionModel().getSelectedItem());
        tableViewCollection.refresh();
    }

    @FXML private void BtnEditMemberConfirm(){
        Members member = (Members) tableViewMembers.getSelectionModel().getSelectedItem();

        if(!TxtEditMemberFirstName.getText().equals("")){
            member.setFirstName(TxtEditMemberFirstName.getText());
        }
        if(!TxtEditMemberLastName.getText().equals("")){
            member.setLastName(TxtEditMemberLastName.getText());
        }
        if(!DataPickerEditMember.getId().equals("")){
            member.setBirthDate(DataPickerEditMember.getValue());
        }

        tableViewMembers.refresh();

        VboxMembers.setDisable(false);
        VboxMembers.setDisable(false);
        VboxMembers.setOpacity(1);
        VboxAddNewMembers.setDisable(true);
        VboxAddNewMembers.setOpacity(0);
        VboxEditMembers.setDisable(true);
        VboxEditMembers.setOpacity(0);
    }

    @FXML private  void BtnCancelEditMember(){
        TxtEditMemberFirstName.setText("");
        TxtEditMemberLastName.setText("");


        VboxMembers.setDisable(false);
        VboxMembers.setOpacity(1);
        VboxAddNewMembers.setDisable(true);
        VboxAddNewMembers.setOpacity(0);
        VboxEditMembers.setDisable(true);
        VboxEditMembers.setOpacity(0);
    }

    @FXML private void BtnEditMemberOnAction(){
        Members member = (Members) tableViewMembers.getSelectionModel().getSelectedItem();
        LblEditMember.setText("Edit Member: " + member.getFirstName());
        TxtEditMemberFirstName.setPromptText(member.getFirstName());
        TxtEditMemberLastName.setPromptText(member.getLastName());
        DataPickerEditMember.setPromptText(member.getBirthDate().toString());


        VboxMembers.setDisable(true);
        VboxMembers.setOpacity(0);
        VboxAddNewMembers.setDisable(true);
        VboxAddNewMembers.setOpacity(0);
        VboxEditMembers.setDisable(false);
        VboxEditMembers.setOpacity(1);
    }

    @FXML private void BtnEditItemsOnAction(){
        Items item = (Items) tableViewCollection.getSelectionModel().getSelectedItem();
        LblEditItems.setText("Edit item: " + item.getTitle());
        TxtEditItemTitle.setPromptText(item.getTitle());
        TxtEditItemsAuthor.setPromptText(item.getAuthor());

        VboxCollection.setDisable(true);
        VboxCollection.setOpacity(0);
        VboxAddNewItem.setDisable(true);
        VboxAddNewItem.setOpacity(0);
        VboxEditItems.setDisable(false);
        VboxEditItems.setOpacity(1);
    }
    @FXML private void BtnCancelEditItems(){
        TxtEditItemTitle.setText("");
        TxtEditItemsAuthor.setText("");

        VboxCollection.setDisable(false);
        VboxCollection.setOpacity(1);
        VboxAddNewItem.setDisable(true);
        VboxAddNewItem.setOpacity(0);
        VboxEditItems.setDisable(true);
        VboxEditItems.setOpacity(0);
    }

    @FXML private void BtnEditItemConfirm(){
        Items item = (Items) tableViewCollection.getSelectionModel().getSelectedItem();

        if(!TxtEditItemTitle.getText().equals("")){
            item.setTitle(TxtEditItemTitle.getText());
        }
        if(!TxtEditItemsAuthor.getText().equals("")){
            item.setAuthor(TxtEditItemsAuthor.getText());
        }

        tableViewCollection.refresh();

        VboxCollection.setDisable(false);
        VboxCollection.setOpacity(1);
        VboxAddNewItem.setDisable(true);
        VboxAddNewItem.setOpacity(0);
        VboxEditItems.setDisable(true);
        VboxEditItems.setOpacity(0);
    }
}
