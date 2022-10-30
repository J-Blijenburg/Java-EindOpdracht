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
import java.time.format.DateTimeFormatter;

public class AddMemberController  {

    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Members> listOfMembers;
    @FXML private TextField txtAddMemberFirstName;
    @FXML private TextField txtAddMemberLastName;
    @FXML private DatePicker dataPickerAddNewMember;
    @FXML private Label lblAddNewMemberErrorMessage;
    @FXML private TableView<Members> tableViewMembers;

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
        setSceneOfMembers(new MembersController(anchorPane, listOfMembers));
    }
    //When entering all the needed information you can add the new member
    @FXML public void btnAddMemberConfirm(ActionEvent event) {
        try{
            if(txtAddMemberFirstName.getText().equals("")| txtAddMemberLastName.getText().equals("") | dataPickerAddNewMember.getEditor().getText().equals("") ){
                throw new Exception("Please, fill in all the fields");
            }
            LocalDate dateOfBirth = checkDate(dataPickerAddNewMember);

            int newMemberId = listOfMembers.get(listOfMembers.size() - 1).getId() + 1;

            listOfMembers.add(new Members(newMemberId,txtAddMemberFirstName.getText(), txtAddMemberLastName.getText(), dateOfBirth , txtAddMemberFirstName.getText(), txtAddMemberLastName.getText() +  "123"));

            setSceneOfMembers(new MembersController(anchorPane, listOfMembers));
        }
        catch(Exception ex){
            lblAddNewMemberErrorMessage.setText(ex.getMessage());
        }
    }

    //set the scene with the given controller and fxml-file
    private void setSceneOfMembers(Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Members-View.fxml"));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }

    private LocalDate checkDate(DatePicker dateTimePicker) throws Exception {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(dateTimePicker.getEditor().getText(), formatter);

        }catch (Exception ex){
            throw new Exception("Please, enter a valid date. For Example: 11-02-2000");
        }
    }

}
