package com.example.javaeindopdracht;

import Database.Database;
import Model.Items;
import Model.Members;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private TableView<Members> tableViewMembers;
    @FXML private TableView<Items> tableViewCollection;
    @FXML private TextField txtItemCode;
    @FXML private TextField txtMemberIdentifier;
    @FXML private TextField txtReceiveItemCode;
    @FXML private TextField txtAddMemberFirstName;
    @FXML private TextField txtAddMemberLastName;
    @FXML private TextField txtAddItemsTitle;
    @FXML private TextField txtAddItemsAuthor;
    @FXML private TextField txtEditItemTitle;
    @FXML private TextField txtEditItemsAuthor;
    @FXML private TextField txtEditMemberFirstName;
    @FXML private TextField txtEditMemberLastName;
    @FXML private TextField txtSearchItem;
    @FXML private TextField txtSearchMember;
    @FXML private Label lblLendItemSuccses;
    @FXML private Label lblLendItemError;
    @FXML private Label LblWelcome;
    @FXML private Label lblReceiveItemError;
    @FXML private Label lblReceiveItemSuccses;
    @FXML private Label lblEditMember;
    @FXML private Label lblEditItemsErrorMessage;
    @FXML private Label lblEditItems;
    @FXML private Label lblAddNewMemberErrorMessage;
    @FXML private Label lblMembersErrorMessage;
    @FXML private Label lblItemsErrorMessage;
    @FXML private VBox vboxMembers;
    @FXML private VBox vboxAddNewMembers;
    @FXML private VBox vboxEditMembers;
    @FXML private VBox vboxCollection;
    @FXML private VBox vboxAddNewItem;
    @FXML private VBox vboxEditItems;
    @FXML private DatePicker dataPickerAddNewMember;
    @FXML private DatePicker dataPickerEditMember;
    @FXML private TabPane tabPane;
    @FXML private Tab tabLendingReceiving;
    @FXML private TableColumn<Items, String> tableViewItemsLendOutBy;
    @FXML private TableColumn<Items, String > tableViewItemsAvailable;
    @FXML private TableColumn<Members, String > tableViewMembersBirthDay;
    @FXML public AnchorPane dashBoardAnchorPane;
    private int dateCheck;
    private final int deadLine = 21;
    private final int totalDaysToLate = dateCheck - deadLine;
    private final Database database;
    private final Members currentMember;
    private ObservableList<Members> listOfMembers;
    private ObservableList<Items> listOfItems;

    //Receive the current member and database from the Login-controller
    public DashboardController(Members member, Database database){
        this.currentMember = member;
        this.database = database;
    }
    //Greet the current member and load all the list in the correct tableView. It also makes sure that the right Tap is opened
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //this.listOfMembers = FXCollections.observableList(database.getAllMembers());
        //this.listOfItems = FXCollections.observableList(database.getAllItems());
        //LblWelcome.setText("Welcome " + currentMember.getFirstName() + " " + currentMember.getLastName());
        //tableViewMembers.setItems(listOfMembers);
        //tableViewCollection.setItems(listOfItems);
        //tabPane.getSelectionModel().select(tabLendingReceiving);

        //ChangeTableViewAvailable();
        //ChangeTableViewLendOutBy();
        //ChangeTableViewBirthDate();
        //SelectionChangedTab();

        //txtSearchItem.textProperty().addListener((obs, oldText, newText) -> SearchItem());

        //txtSearchMember.textProperty().addListener((obs, oldText, newText) -> SearchMember());

    }
    @FXML private void membersOnAction() throws IOException{
        setScene(new MembersController(dashBoardAnchorPane),"Members-View.fxml");
    }
    @FXML private void collectionOnAction() throws IOException{
        setScene(new CollectionController(dashBoardAnchorPane), "Collection-View.fxml");
    }

    @FXML private void lendingReceivingOnAction() throws IOException {
        setScene(new LendingReceivingController(), "LendingReceiving-View.fxml");
    }

    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        //https://stackoverflow.com/questions/53127331/javafx-swap-anchorpane-element-with-fxml-from-another-file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        dashBoardAnchorPane.getChildren().setAll(an);
    }

    //Changes the birthdate cell of members listview to the correct date format
    private void ChangeTableViewBirthDate(){
        tableViewMembersBirthDay.setCellValueFactory(cellData -> {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return new SimpleStringProperty(dateFormat.format(cellData.getValue().getBirthDate()));
        });
    }

    //Receives the data what in the cell is displayed and change it to the right string
    private void ChangeTableViewLendOutBy(){
        tableViewItemsLendOutBy.setCellValueFactory(cellData -> {
            Items item = cellData.getValue();
            if(item.getLendOutBy() != null){
                return new SimpleStringProperty(cellData.getValue().getLendOutBy().getFirstName());
            }
            return new SimpleStringProperty(null);
        });
    }

    private void SelectionChangedTab(){
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            RefreshTableView();
            RefreshLabels();
            RefreshTxtField();
        });
    }
    //Receives the data what in the cell is displayed and change it to the right string
    private void ChangeTableViewAvailable(){
        tableViewItemsAvailable.setCellValueFactory(cellData -> {
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
            Items selectedItem = SelectItem(CheckForInt(txtItemCode.getText()));
            CheckNull(selectedItem, "Member does not exist");

            Members selectedMember = SelectMember(CheckForInt(txtMemberIdentifier.getText()));
            CheckNull(selectedMember, "Item not found in this library");

            if(selectedItem.getAvailable().equals(true)){
                selectedItem.setLendOutDate(LocalDate.now());
                selectedItem.setLendOutBy(selectedMember);
                selectedItem.setAvailable(false);
            }
            else{
                throw new Exception("Item is not available now");
            }
            lblLendItemSuccses.setText("Item is lent out successfully");
            RefreshTableView();
        }catch(Exception ex){
            lblLendItemError.setText(ex.getMessage());
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
    private Items SelectItem(int selectedItem) throws Exception {
        for(Items item : listOfItems){
            if(item.getItemCode() == selectedItem){
                return item;
            }
        }
        throw new Exception("Item not recognized");
    }

    //Receive the selected item and check if the item is lent out by the current member
    @FXML private void BtnReceiveItemOnAction(){
        try{
            ClearCurrentTextOfLabel();
            Items item = SelectItem(CheckForInt(txtReceiveItemCode.getText()));

            CalculateLendTime(item);
            checkLendOutBy(item);

            CheckNull(item, "Item not recognized");

        }
        catch (Exception ex){
            lblReceiveItemError.setText(ex.getMessage());
        }
    }
    //check if the member actually lend the item
    private void checkLendOutBy(Items item) throws Exception {
        if(item.getLendOutBy().getId() == (currentMember.getId())){
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
    private void CalculateLendTime(Items item){
        dateCheck = LocalDate.now().getDayOfYear() - item.getLendOutDate().getDayOfYear();
    }

    //If the object is null throw an exception
    private void CheckNull(Object object, String errorMessage) throws Exception {
        if (object == null) {
            throw new Exception(errorMessage);
        }
    }
    //set the current text of the label to not
    private void ClearCurrentTextOfLabel(){
        lblLendItemError.setText(null);
        lblLendItemSuccses.setText(null);
        lblReceiveItemSuccses.setText(null);
        lblReceiveItemError.setText(null);
    }





    //~~~~~~~~~~~~~~~~~~~~~~~~~~Everything of the member TapPane~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //It will show the right tab to create a new object of in this case a new member
    @FXML private void BtnAddMemberOnAction(){
        GoToNewObjectPage(vboxMembers, vboxAddNewMembers, vboxEditMembers);
    }

    //When entering all the needed information you can add the new member
    @FXML private void BtnAddMemberConfirm(){
        try{
            if(txtAddMemberFirstName.getText() == null | txtAddMemberLastName.getText() == null | dataPickerAddNewMember.getValue() == null ){
                throw new Exception("Please, fill in all the fields");
            }
            listOfMembers.add(new Members(listOfMembers.size() + 1,txtAddMemberFirstName.getText(), txtAddMemberLastName.getText(), LocalDate.of(dataPickerAddNewMember.getValue().getYear(), dataPickerAddNewMember.getValue().getMonth(), dataPickerAddNewMember.getValue().getDayOfMonth()) , txtAddMemberFirstName.getText(), txtAddMemberLastName.getText() +  "123"));
            tableViewMembers.refresh();
            GoToMainPage(vboxMembers, vboxAddNewMembers, vboxEditMembers);

        }
        catch(Exception ex){
            lblAddNewMemberErrorMessage.setText(ex.getMessage());
        }
    }
    //Check if a member is selected and bring that member to the edit-page.
    @FXML private void BtnEditMemberOnAction(){
        try{
            if(tableViewMembers.getSelectionModel().getSelectedItem() != null){
                Members member = tableViewMembers.getSelectionModel().getSelectedItem();
                lblEditMember.setText("Edit Member: " + member.getFirstName());
                txtEditMemberFirstName.setPromptText(member.getFirstName());
                txtEditMemberLastName.setPromptText(member.getLastName());
                dataPickerEditMember.setPromptText(member.getBirthDate().toString());

                GoToEditObjectPage(vboxMembers, vboxAddNewMembers, vboxEditMembers);
            }
            else{
                throw new Exception("Please, Select an member");
            }
        }catch(Exception ex){
            lblMembersErrorMessage.setText(ex.getMessage());
        }
    }
    //If information of the member is changed this button is going to confirm the changes
    @FXML private void BtnEditMemberConfirm(){
        Members member = tableViewMembers.getSelectionModel().getSelectedItem();

        if(!txtEditMemberFirstName.getText().equals("")){
            member.setFirstName(txtEditMemberFirstName.getText());
        }
        if(!txtEditMemberLastName.getText().equals("")){
            member.setLastName(txtEditMemberLastName.getText());
        }
        if(!dataPickerEditMember.getId().equals("")){
            member.setBirthDate(dataPickerEditMember.getValue());
        }

        tableViewMembers.refresh();

        GoToMainPage(vboxMembers, vboxAddNewMembers, vboxEditMembers);
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
            lblMembersErrorMessage.setText(ex.getMessage());
        }
    }
    //While editing a member cancel the process and reset all the values
    @FXML private  void BtnCancelEditMember(){
        txtEditMemberFirstName.setText("");
        txtEditMemberLastName.setText("");
        dataPickerEditMember.setValue(null);
        RefreshLabels();
        GoToMainPage(vboxMembers, vboxAddNewMembers, vboxEditMembers);
    }
    //While creating a new member cancel the process and reset all the values
    @FXML private void BtnCancelNewMember(){
        txtAddMemberFirstName.setText("");
        txtAddMemberLastName.setText("");
        dataPickerAddNewMember.setValue(null);
        RefreshLabels();
        GoToMainPage(vboxMembers, vboxAddNewMembers, vboxEditMembers);
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~Everything of the Item TapPane~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //It will show the right tab to create a new object of in this case a new Item
    @FXML private void BtnAddItemOnAction(){
        GoToNewObjectPage(vboxCollection, vboxAddNewItem, vboxEditItems);
    }

    //When entering all the needed information you can add the new item
    @FXML private void BtnAddItemConfirm(){
        try{
            if(txtAddItemsTitle.getText() != null | txtAddItemsAuthor.getText() != null){
                listOfItems.add(new Items(listOfItems.size() + 1, true, txtAddItemsTitle.getText(), txtAddItemsAuthor.getText()));
                GoToMainPage(vboxCollection, vboxAddNewItem, vboxEditItems);
            }
            else{
                throw new Exception("Please, fill in all the fields");
            }
        }catch(Exception ex){
            lblEditItemsErrorMessage.setText(ex.getMessage());
        }
    }
    //Check if an item is selected and bring that item to the edit-page.
    @FXML private void BtnEditItemsOnAction(){
        try{
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                Items item = tableViewCollection.getSelectionModel().getSelectedItem();
                lblEditItems.setText("Edit item: " + item.getTitle());
                txtEditItemTitle.setPromptText(item.getTitle());
                txtEditItemsAuthor.setPromptText(item.getAuthor());

                GoToEditObjectPage(vboxCollection, vboxAddNewItem, vboxEditItems);
            }
            else{
                throw new Exception("Please, Select an item");
            }
        }catch(Exception ex){
            lblItemsErrorMessage.setText(ex.getMessage());
        }
    }
    //If information of the selected item is changed this button is going to confirm the changes
    @FXML private void BtnEditItemConfirm(){

        try{
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                Items item = tableViewCollection.getSelectionModel().getSelectedItem();

                if(!txtEditItemTitle.getText().equals("")){
                    item.setTitle(txtEditItemTitle.getText());
                }
                if(!txtEditItemsAuthor.getText().equals("")){
                    item.setAuthor(txtEditItemsAuthor.getText());
                }
                tableViewCollection.refresh();
                GoToMainPage(vboxCollection, vboxAddNewItem, vboxEditItems);
            }
            else{
                throw new Exception("Please, Select an item");
            }

        }catch(Exception ex){
            lblItemsErrorMessage.setText(ex.getMessage());
        }
    }
    //Check if an item is selected. If so delete the item
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
            lblItemsErrorMessage.setText(ex.getMessage());
        }
    }

    //While creating a new item cancel the process and reset all the values
    @FXML private void BtnCancelNewItem(){
        txtAddItemsAuthor.setText("");
        txtAddItemsTitle.setText("");
        GoToMainPage(vboxCollection, vboxAddNewItem, vboxEditItems);
        RefreshLabels();

    }
    //While editing an item cancel the process and reset all the values
    @FXML private void BtnCancelEditItems(){
        txtEditItemTitle.setText("");
        txtEditItemsAuthor.setText("");
        RefreshLabels();
        GoToMainPage(vboxCollection, vboxAddNewItem, vboxEditItems);
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
        lblItemsErrorMessage.setText("");
        lblMembersErrorMessage.setText("");
    }

    //Refresh the chosen tableView
    private void RefreshTableView(){
        tableViewMembers.refresh();
        tableViewCollection.refresh();
    }

    //Refresh the chosen Txtfields
    private void RefreshTxtField(){
        txtItemCode.setText("");
        txtMemberIdentifier.setText("");
        txtReceiveItemCode.setText("");

    }

    //search function for items
    private void SearchItem(){
        String searchItem = txtSearchItem.getText().toLowerCase();
        if(txtSearchItem.getText().equals("")){
            tableViewCollection.setItems(listOfItems);
        }
        else{
            ObservableList<Items> filter = FXCollections.observableArrayList();
            for(Items item : listOfItems){
                if(item.getTitle().toLowerCase().contains(searchItem) | item.getAuthor().toLowerCase().contains(searchItem)){
                    filter.add(item);
                }
            }
            tableViewCollection.setItems(filter);
        }
    }
    //search function for members
    private void SearchMember(){
        String searchMember = txtSearchMember.getText().toLowerCase();
        if(txtSearchMember.getText().equals("")){
            tableViewMembers.setItems(listOfMembers);
        }
        else{
            ObservableList<Members> filter = FXCollections.observableArrayList();
            for(Members members : listOfMembers){
                if(members.getFirstName().toLowerCase().contains(searchMember) | members.getLastName().toLowerCase().contains(searchMember)){
                    filter.add(members);
                }
            }
            tableViewMembers.setItems(filter);
        }
    }

}
