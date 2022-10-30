package com.example.javaeindopdracht;

import Model.Members;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MembersController implements Initializable {
    @FXML private TableColumn<Members, String > tableViewMembersBirthDay;
    @FXML private TextField txtSearchMember;
    @FXML private AnchorPane anchorPane;
    @FXML private Label lblMembersErrorMessage;
    @FXML private ObservableList<Members> listOfMembers;
    @FXML private TableView<Members> tableViewMembers;

    @FXML private Label lblEditMember;


    public MembersController(AnchorPane anchorPane, ObservableList<Members> listOfMembers) {
        this.anchorPane = anchorPane;
        this.listOfMembers = listOfMembers;
    }
    //Check if a member is selected. If so delete the member
    @FXML public void btnDeleteMemberOnAction(ActionEvent event) {
        try{
            if(tableViewMembers.getSelectionModel().getSelectedItem() != null){
                listOfMembers.remove(tableViewMembers.getSelectionModel().getSelectedItem());
                tableViewMembers.setItems(listOfMembers);
                txtSearchMember.setText("");
            }
            else{
                throw new Exception("Please, Select an member");
            }
        }catch(Exception ex){
            lblMembersErrorMessage.setText(ex.getMessage());
        }
    }
    //Check if a member is selected and bring that member to the edit-page.
    @FXML public void btnEditMemberOnAction(ActionEvent event) {
        try{
            if(tableViewMembers.getSelectionModel().getSelectedItem() != null){
                setScene(new EditMemberController(anchorPane, listOfMembers, tableViewMembers), "EditMember-View.fxml");
            }
            else{
                throw new Exception("Please, Select an member");
            }
        }catch(Exception ex){
            lblMembersErrorMessage.setText(ex.getMessage());
        }

    }
    //it will show the view on how to add a new member
    @FXML public void btnAddMemberOnAction(ActionEvent event) throws IOException {
        setScene(new AddMemberController(anchorPane, listOfMembers, tableViewMembers), "AddMember-View.fxml");
    }

    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        //https://stackoverflow.com/questions/53127331/javafx-swap-anchorpane-element-with-fxml-from-another-file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            tableViewMembers.setItems(listOfMembers);
            ChangeTableViewBirthDate();
            txtSearchMember.textProperty().addListener((obs, oldText, newText) -> SearchMember());
        }catch(Exception ex){
           lblMembersErrorMessage.setText(ex.getMessage());
        }

    }
    //Changes the birthdate cell of members listview to the correct date format
    private void ChangeTableViewBirthDate(){
        tableViewMembersBirthDay.setCellValueFactory(cellData -> {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return new SimpleStringProperty(dateFormat.format(cellData.getValue().getBirthDate()));
        });
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
