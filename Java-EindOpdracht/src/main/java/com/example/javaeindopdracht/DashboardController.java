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
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private ImageView DashBoardImage;
    @FXML private TableView<Members> tableViewMembers;
    @FXML private TableView<Items> tableViewCollection;
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
    @FXML private Label LblEditItemsErrorMessage;
    @FXML private Label LblEditItems;
    @FXML private Label LblAddNewMemberErrorMessage;
    @FXML private Label LblAddNewMemberMessage;
    @FXML private Label LblEditItemsMessage;
    @FXML private Label LblMembersErrorMessage;
    @FXML private Label LblItemsErrorMessage;
    @FXML private VBox VboxMembers;
    @FXML private VBox VboxAddNewMembers;
    @FXML private VBox VboxEditMembers;
    @FXML private VBox VboxCollection;
    @FXML private VBox VboxAddNewItem;
    @FXML private VBox VboxEditItems;
    @FXML private DatePicker DataPickerAddNewMember;
    @FXML private DatePicker DataPickerEditMember;
    @FXML private TabPane TabPane;
    @FXML private Tab TabLendingReceiving;
    private final Members currentMember;
    private final ObservableList<Members> listOfMembers;
    private final ObservableList<Items> listOfItems;
    private final Database database;

    //Receive the current member and database from the Login-controller
    public DashboardController(Members member, Database database){
       this.currentMember = member;
       this.database = database;
        this.listOfMembers = FXCollections.observableList(this.database.getAllMembers());
        this.listOfItems = FXCollections.observableList(this.database.getAllItems());
    }
    //Greet the current member and load all the list in the correct tableView. It also makes sure that the right Tap is opened
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LblWelcome.setText("Welcome " + currentMember.getFirstName() + " " + currentMember.getLastName());
        tableViewMembers.setItems(listOfMembers);
        tableViewCollection.setItems(listOfItems);
        TabPane.getSelectionModel().select(TabLendingReceiving);
    }

    //Check if both Textboxes are filled. Assign the selected item to the selected member
    @FXML private void BtnLendItemOnAction(ActionEvent event){
        try{
            ClearCurrentTextOfLabel();
            EmptyTextBoxCheck(TxtItemCode.getText());
            EmptyTextBoxCheck(TxtMemberIdentifier.getText());

            Items selectedItem = SelectItem(Integer.parseInt(TxtItemCode.getText()));
            CheckNull(selectedItem, "Member does not exist");

            Members selectedMember = SelectMember(Integer.parseInt(TxtMemberIdentifier.getText()));
            CheckNull(selectedMember, "Item not found in this library");

            if(selectedItem.getAvailable().equals(true)){
                selectedItem.setLendOutBy(selectedMember);
                selectedItem.setAvailable(false);
            }
            else{
                throw new Exception("Item is not available now");
            }
            LblLendItemSuccses.setText("Item is lent out successfully");
        }catch(Exception ex){
            LblLendItemError.setText(ex.getMessage());
        }
    }
    //Goes through every member to search the member with the correct member id
    private Members SelectMember(int selectedMember){
        for(Members member : listOfMembers){
            if(member.getId() == selectedMember){
                return member;
            }
        }
        return null;
    }
    //Goes through every item to search the item with the correct item code
    private Items SelectItem(int selectedItem){
        for(Items item : listOfItems){
            if(item.getItemCode() == selectedItem){
                return item;
            }
        }
        return null;
    }

    //Receive the selected item and check if the item is lent out by the current member
    @FXML private void BtnReceiveItemOnAction(){
        try{
            ClearCurrentTextOfLabel();
            EmptyTextBoxCheck(TxtReceiveItemCode.getText());
            Items item = SelectItem(Integer.parseInt(TxtReceiveItemCode.getText()));

            CheckNull(item, "Item not recognized");

            if(item.getLendOutBy().equals(currentMember)){
                if(item.getAvailable().equals(false)){
                    item.setAvailable(true);
                    LblReceiveItemSuccses.setText("Received item successfully");
                }
                else {
                    throw new Exception("Item never been lend out");
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
    //If the object is null throw an exception
    private void CheckNull(Object object, String errorMessage) throws Exception {
        if(object == null){
            throw new Exception(errorMessage);
        }
    }
    //if the textbox is empty throw an exception
    private void EmptyTextBoxCheck(String textBox) throws Exception {
        if(textBox.isEmpty()){
            throw new Exception("Please, enter a value");
        }
    }
    //set the current text of the label to not
    private void ClearCurrentTextOfLabel(){
        LblLendItemError.setText(null);
        LblLendItemSuccses.setText(null);
        LblReceiveItemSuccses.setText(null);
        LblReceiveItemError.setText(null);
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~Evrything of the member TapPane~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @FXML private void BtnAddMemberOnAction(){
        GoToNewObjectPage(VboxMembers, VboxAddNewMembers, VboxEditMembers);
    }
    @FXML private void BtnAddMemberConfirm(){
        try{
            if(TxtAddMemberFirstName.getText() == null | TxtAddMemberLastName.getText() == null | DataPickerAddNewMember.getValue() == null ){
                throw new Exception("Please, fill in all the fields");
            }
            listOfMembers.add(new Members(listOfMembers.size() + 1,TxtAddMemberFirstName.getText(), TxtAddMemberLastName.getText(), LocalDate.of(DataPickerAddNewMember.getValue().getYear(), DataPickerAddNewMember.getValue().getMonth(), DataPickerAddNewMember.getValue().getDayOfMonth()) , TxtAddMemberFirstName.getText(), TxtAddMemberLastName +  "123"));

            tableViewMembers.refresh();

            GoToMainPage(VboxMembers, VboxAddNewMembers, VboxEditMembers);
        }
        catch(Exception ex){
            LblAddNewMemberErrorMessage.setText(ex.getMessage());
        }
    }
    //If information of the member is changed this button is going to confirm the changes
    @FXML private void BtnEditMemberConfirm(){
        Members member = tableViewMembers.getSelectionModel().getSelectedItem();

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

        GoToMainPage(VboxMembers, VboxAddNewMembers, VboxEditMembers);
    }
    //Check if a member is selected and bring that member to the editpage.
    @FXML private void BtnEditMemberOnAction(){
        try{
            if(tableViewMembers.getSelectionModel().getSelectedItem() != null){
                Members member = tableViewMembers.getSelectionModel().getSelectedItem();
                LblEditMember.setText("Edit Member: " + member.getFirstName());
                TxtEditMemberFirstName.setPromptText(member.getFirstName());
                TxtEditMemberLastName.setPromptText(member.getLastName());
                DataPickerEditMember.setPromptText(member.getBirthDate().toString());

                GoToEditObjectPage(VboxMembers, VboxAddNewMembers, VboxEditMembers);
            }
            else{
                throw new Exception("Please, Select an member");
            }
        }catch(Exception ex){
            LblMembersErrorMessage.setText(ex.getMessage());
        }
    }
    //Check if a member is selected. If so delete the member
    @FXML private void BtnDeleteMember(){
        try{
            if(tableViewMembers.getSelectionModel().getSelectedItem() != null){
                listOfMembers.remove(tableViewMembers.getSelectionModel().getSelectedItem());
                tableViewMembers.refresh();
            }
            else{
                throw new Exception("Please, Select an member");
            }
        }catch(Exception ex){
            LblMembersErrorMessage.setText(ex.getMessage());
        }
    }
    //While editing a member cancel the process and reset all the values
    @FXML private  void BtnCancelEditMember(){
        TxtEditMemberFirstName.setText("");
        TxtEditMemberLastName.setText("");
        DataPickerEditMember.setValue(null);
        RefreshLabels();
        GoToMainPage(VboxMembers, VboxAddNewMembers, VboxEditMembers);
    }
    //While creating a new member cancel the process and reset all the values
    @FXML private void BtnCancelNewMember(){
        TxtAddMemberFirstName.setText("");
        TxtAddMemberLastName.setText("");
        DataPickerAddNewMember.setValue(null);
        RefreshLabels();
        GoToMainPage(VboxMembers, VboxAddNewMembers, VboxEditMembers);
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~Everything of the Item TapPane~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @FXML private void BtnAddItemOnAction(){
        GoToNewObjectPage(VboxCollection, VboxAddNewItem, VboxEditItems);
    }

    @FXML private void BtnAddItemConfirm(){
        try{
            if(TxtAddItemsTitle.getText() != null | TxtAddItemsAuthor.getText() != null){
                listOfItems.add(new Items(listOfItems.size() + 1, true, TxtAddItemsTitle.getText(),TxtAddItemsAuthor.getText()));
                GoToMainPage(VboxCollection, VboxAddNewItem, VboxEditItems);
            }
            else{
                throw new Exception("Please, fill in all the fields");
            }
        }catch(Exception ex){
            LblEditItemsErrorMessage.setText(ex.getMessage());
        }
    }

    @FXML private void BtnEditItemsOnAction(){
        try{
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                Items item = tableViewCollection.getSelectionModel().getSelectedItem();
                LblEditItems.setText("Edit item: " + item.getTitle());
                TxtEditItemTitle.setPromptText(item.getTitle());
                TxtEditItemsAuthor.setPromptText(item.getAuthor());

                GoToEditObjectPage(VboxCollection, VboxAddNewItem, VboxEditItems);
            }
            else{
                throw new Exception("Please, Select an item");
            }
        }catch(Exception ex){
            LblItemsErrorMessage.setText(ex.getMessage());
        }
    }
    @FXML private void BtnEditItemConfirm(){

        try{
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                Items item = tableViewCollection.getSelectionModel().getSelectedItem();

                if(!TxtEditItemTitle.getText().equals("")){
                    item.setTitle(TxtEditItemTitle.getText());
                }
                if(!TxtEditItemsAuthor.getText().equals("")){
                    item.setAuthor(TxtEditItemsAuthor.getText());
                }
                tableViewCollection.refresh();
                GoToMainPage(VboxCollection, VboxAddNewItem, VboxEditItems);
            }
            else{
                throw new Exception("Please, Select an item");
            }

        }catch(Exception ex){
            LblItemsErrorMessage.setText(ex.getMessage());
        }
    }
    @FXML private void BtnDeleteItem(){

        try{
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                listOfItems.remove(tableViewCollection.getSelectionModel().getSelectedItem());
                tableViewCollection.refresh();
            }
            else{
                throw new Exception("Please, Select an item");
            }
        }catch (Exception ex){
            LblItemsErrorMessage.setText(ex.getMessage());
        }
    }
    @FXML private void BtnCancelNewItem(){
        TxtAddItemsAuthor.setText("");
        TxtAddItemsTitle.setText("");
        GoToMainPage(VboxCollection, VboxAddNewItem, VboxEditItems);
        RefreshLabels();

    }
    @FXML private void BtnCancelEditItems(){
        TxtEditItemTitle.setText("");
        TxtEditItemsAuthor.setText("");
        RefreshLabels();
        GoToMainPage(VboxCollection, VboxAddNewItem, VboxEditItems);
    }



    //This method will make sure that the right Vbox is shown
    private void GoToMainPage(VBox vbox1, VBox vBox2, VBox vBox3){
        vbox1.setDisable(false);
        vbox1.setOpacity(1);
        vBox2.setDisable(true);
        vBox2.setOpacity(0);
        vBox3.setDisable(true);
        vBox3.setOpacity(0);
    }
    private void GoToNewObjectPage(VBox vbox1, VBox vBox2, VBox vBox3){
        vbox1.setDisable(true);
        vbox1.setOpacity(0);
        vBox2.setDisable(false);
        vBox2.setOpacity(1);
        vBox3.setDisable(true);
        vBox3.setOpacity(0);
    }
    private void GoToEditObjectPage(VBox vbox1, VBox vBox2, VBox vBox3){
        vbox1.setDisable(true);
        vbox1.setOpacity(0);
        vBox2.setDisable(true);
        vBox2.setOpacity(0);
        vBox3.setDisable(false);
        vBox3.setOpacity(1);
    }


    //Reset the chosen labels
    private void RefreshLabels(){
        LblItemsErrorMessage.setText("");
        LblMembersErrorMessage.setText("");
    }
}
