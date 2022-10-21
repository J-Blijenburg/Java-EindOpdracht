package com.example.javaeindopdracht;

import Database.Database;
import Model.Items;
import Model.Members;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TableColumn<Items, String> TableViewItemsLendOutBy;
    @FXML private TableColumn<Items, String > TableViewItemsAvailable;
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

        ChangeTableViewAvailable();
        ChangeTableViewLendOutBy();
        SelectionChangedTab();
    }
    //Receives the data what in the cell is displayed and change it to the right string
    private void ChangeTableViewLendOutBy(){
        TableViewItemsLendOutBy.setCellValueFactory(cellData -> {
            Items item = cellData.getValue();
            if(item.getLendOutBy() != null){
                return new SimpleStringProperty(cellData.getValue().getLendOutBy().getFirstName());
            }
            return new SimpleStringProperty(null);
        });
    }

    private void SelectionChangedTab(){
        TabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                RefreshTableView();
                RefreshLabels();
                RefreshTxtField();
            }
        });
    }
    //Receives the data what in the cell is displayed and change it to the right string
    private void ChangeTableViewAvailable(){
        TableViewItemsAvailable.setCellValueFactory(cellData -> {
            Items item = cellData.getValue();
            if(item.getAvailable()){
                return new SimpleStringProperty("Yes");
            }

            return  new SimpleStringProperty("No");
        });
    }

    //Check if both Textboxes are filled. Assign the selected item to the selected member
    @FXML private void BtnLendItemOnAction(ActionEvent event){
        try{
            ClearCurrentTextOfLabel();
            Items selectedItem = SelectItem(CheckForInt(TxtItemCode.getText()));
            CheckNull(selectedItem, "Member does not exist");

            Members selectedMember = SelectMember(CheckForInt(TxtMemberIdentifier.getText()));
            CheckNull(selectedMember, "Item not found in this library");

            if(selectedItem.getAvailable().equals(true)){
                selectedItem.setLendOutDate(LocalDate.now());
                selectedItem.setLendOutBy(selectedMember);
                selectedItem.setAvailable(false);
            }
            else{
                throw new Exception("Item is not available now");
            }
            LblLendItemSuccses.setText("Item is lent out successfully");
            RefreshTableView();
        }catch(Exception ex){
            LblLendItemError.setText(ex.getMessage());
        }
    }
    private Integer CheckForInt(String text) throws Exception {
        try{
            return Integer.parseInt(text);
        }catch (NumberFormatException ex){
            throw new Exception("Please, Enter a valid number");
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
            Items item = SelectItem(CheckForInt(TxtReceiveItemCode.getText()));

            int dateCheck = LocalDate.now().getDayOfYear() - item.getLendOutDate().getDayOfYear();
            int deadLine = 2;
            int totalDaysToLate = dateCheck - deadLine;

            CheckNull(item, "Item not recognized");

            if(item.getLendOutBy().equals(currentMember)){
                if(item.getAvailable().equals(false)){
                    if(dateCheck < deadLine){
                        LblReceiveItemSuccses.setText("Received item successfully");
                    }
                    else{
                        LblReceiveItemError.setText("Item is " + totalDaysToLate + " days to late!");
                    }
                    item.setAvailable(true);
                    item.setLendOutBy(null);
                    item.setLendOutDate(null);
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
        if (object == null) {
            throw new Exception(errorMessage);
        }
    }
    //set the current text of the label to not
    private void ClearCurrentTextOfLabel(){
        LblLendItemError.setText(null);
        LblLendItemSuccses.setText(null);
        LblReceiveItemSuccses.setText(null);
        LblReceiveItemError.setText(null);
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~Everything of the member TapPane~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //It will show the right tab to create a new object of in this case a new member
    @FXML private void BtnAddMemberOnAction(){
        GoToNewObjectPage(VboxMembers, VboxAddNewMembers, VboxEditMembers);
    }

    //When entering all the needed information you can add the new member
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
    //It will show the right tab to create a new object of in this case a new Item
    @FXML private void BtnAddItemOnAction(){
        GoToNewObjectPage(VboxCollection, VboxAddNewItem, VboxEditItems);
    }

    //When entering all the needed information you can add the new item
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
    //Check if a item is selected and bring that item to the editpage.
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
    //If information of the selected item is changed this button is going to confirm the changes
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
    //Check if a item is selected. If so delete the item
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

    //While creating a new item cancel the process and reset all the values
    @FXML private void BtnCancelNewItem(){
        TxtAddItemsAuthor.setText("");
        TxtAddItemsTitle.setText("");
        GoToMainPage(VboxCollection, VboxAddNewItem, VboxEditItems);
        RefreshLabels();

    }
    //While editing an item cancel the process and reset all the values
    @FXML private void BtnCancelEditItems(){
        TxtEditItemTitle.setText("");
        TxtEditItemsAuthor.setText("");
        RefreshLabels();
        GoToMainPage(VboxCollection, VboxAddNewItem, VboxEditItems);
    }



    //These methods will make sure that the right Vbox is shown
    //the 'Main page' of the TabPane
    private void GoToMainPage(VBox vbox1, VBox vBox2, VBox vBox3){
        vbox1.setDisable(false);
        vbox1.setOpacity(1);
        vBox2.setDisable(true);
        vBox2.setOpacity(0);
        vBox3.setDisable(true);
        vBox3.setOpacity(0);
    }
    //the 'Create a new object' of the TabPane
    private void GoToNewObjectPage(VBox vbox1, VBox vBox2, VBox vBox3){
        vbox1.setDisable(true);
        vbox1.setOpacity(0);
        vBox2.setDisable(false);
        vBox2.setOpacity(1);
        vBox3.setDisable(true);
        vBox3.setOpacity(0);
    }
    //the 'edit a selected object' of the TabPane
    private void GoToEditObjectPage(VBox vbox1, VBox vBox2, VBox vBox3){
        vbox1.setDisable(true);
        vbox1.setOpacity(0);
        vBox2.setDisable(true);
        vBox2.setOpacity(0);
        vBox3.setDisable(false);
        vBox3.setOpacity(1);
    }

    //Refresh the chosen labels
    private void RefreshLabels(){
        LblItemsErrorMessage.setText("");
        LblMembersErrorMessage.setText("");
    }

    //Refresh the chosen tableView
    private void RefreshTableView(){
        tableViewMembers.refresh();
        tableViewCollection.refresh();
    }

    //Refresh the chosen Txtfields
    private void RefreshTxtField(){
        TxtItemCode.setText("");
        TxtMemberIdentifier.setText("");
        TxtReceiveItemCode.setText("");

    }
}
