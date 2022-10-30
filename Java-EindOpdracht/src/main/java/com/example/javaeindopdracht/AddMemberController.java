package com.example.javaeindopdracht;

import Model.Members;
import com.example.javaeindopdracht.Exception.DatePickerException;
import com.example.javaeindopdracht.Exception.EmptyFieldsException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddMemberController  {

    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Members> listOfMembers;
    @FXML private TextField txtAddMemberFirstName;
    @FXML private TextField txtAddMemberLastName;
    @FXML private DatePicker dataPickerAddNewMember;
    @FXML private Label lblAddNewMemberErrorMessage;
    @FXML private TableView<Members> tableViewMembers;
    private CreateScene createScene = new CreateScene();

    public AddMemberController(AnchorPane anchorPane, ObservableList<Members> listOfMembers, TableView<Members> tableViewMembers) {
        this.anchorPane = anchorPane;
        this.listOfMembers = listOfMembers;
        this.tableViewMembers = tableViewMembers;
    }

    //While creating a new member cancel the process and reset all the values
    @FXML public void btnCancelNewMember(ActionEvent event) throws IOException {
        txtAddMemberFirstName.setText("");
        txtAddMemberLastName.setText("");
        dataPickerAddNewMember.setValue(null);
        createScene.setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml", anchorPane);
    }
    //When entering all the needed information you can add the new member
    @FXML public void btnAddMemberConfirm(ActionEvent event)  {
        try{
            if(txtAddMemberFirstName.getText().equals("") || txtAddMemberLastName.getText().equals("") || dataPickerAddNewMember.getEditor().getText().equals("") ){
                throw new EmptyFieldsException();
            }
            LocalDate dateOfBirth = checkDate(dataPickerAddNewMember);
            checkDate(dataPickerAddNewMember);

            listOfMembers.add(new Members(listOfMembers.size() + 1,txtAddMemberFirstName.getText(), txtAddMemberLastName.getText(), dateOfBirth , txtAddMemberFirstName.getText(), txtAddMemberLastName.getText() +  "123"));

            createScene.setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml", anchorPane);
        }
        catch(Exception ex){
            lblAddNewMemberErrorMessage.setText(ex.getMessage());
        }
    }
    private LocalDate checkDate(DatePicker dateTimePicker) throws DatePickerException {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(dateTimePicker.getEditor().getText(), formatter);
        }catch(Exception ex){
            throw new DatePickerException();
        }
    }

}
