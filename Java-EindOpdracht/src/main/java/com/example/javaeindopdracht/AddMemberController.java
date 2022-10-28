package com.example.javaeindopdracht;

import Model.Members;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;

public class AddMemberController  {

    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Members> listOfMembers;
    @FXML private TextField txtAddMemberFirstName;
    @FXML private TextField txtAddMemberLastName;
    @FXML private DatePicker dataPickerAddNewMember;
    @FXML private Label lblAddNewMemberErrorMessage;
    @FXML private TableView<Members> tableViewMembers;

    public AddMemberController(AnchorPane anchorPane, TableView<Members> tableViewMembers) {
        this.anchorPane = anchorPane;
        this.tableViewMembers = tableViewMembers;
    }

    @FXML public void btnCancelNewMember(ActionEvent event) throws IOException {
        setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml");
    }
    //When entering all the needed information you can add the new member
    @FXML public void btnAddMemberConfirm(ActionEvent event) {
        try{
            if(txtAddMemberFirstName.getText() == null | txtAddMemberLastName.getText() == null | dataPickerAddNewMember.getValue() == null ){
                throw new Exception("Please, fill in all the fields");
            }
            listOfMembers.add(new Members(listOfMembers.size() + 1,txtAddMemberFirstName.getText(), txtAddMemberLastName.getText(), LocalDate.of(dataPickerAddNewMember.getValue().getYear(), dataPickerAddNewMember.getValue().getMonth(), dataPickerAddNewMember.getValue().getDayOfMonth()) , txtAddMemberFirstName.getText(), txtAddMemberLastName.getText() +  "123"));

            setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml");
        }
        catch(Exception ex){
            lblAddNewMemberErrorMessage.setText(ex.getMessage());
        }
    }

    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
