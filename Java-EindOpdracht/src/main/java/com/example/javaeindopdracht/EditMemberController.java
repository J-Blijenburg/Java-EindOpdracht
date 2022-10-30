package com.example.javaeindopdracht;

import Model.Members;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditMemberController implements Initializable {
    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Members> listOfMembers;
    @FXML private TableView<Members> tableViewMembers;
    @FXML private TextField txtEditMemberFirstName;
    @FXML private TextField txtEditMemberLastName;
    @FXML private DatePicker dataPickerEditMember;
    @FXML private Label lblEditMember;
    @FXML private Label lblEditMemberErrorMessage;
    private Scene scene = new Scene();

    public EditMemberController(AnchorPane anchorPane, ObservableList<Members> listOfMembers, TableView<Members> tableViewMembers) {
        this.anchorPane = anchorPane;
        this.listOfMembers = listOfMembers;
        this.tableViewMembers = tableViewMembers;
    }
    //While editing a member cancel the process and reset all the values
    @FXML public void btnCancelEditMember(ActionEvent event) throws IOException {
        txtEditMemberFirstName.setText("");
        txtEditMemberLastName.setText("");
        dataPickerEditMember.setValue(null);
        scene.setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml", anchorPane);
    }

    //If information of the member is changed this button is going to confirm the changes
    @FXML public void btnEditMemberConfirm(ActionEvent event) {
        try {
            Members member = tableViewMembers.getSelectionModel().getSelectedItem();
            if(!txtEditMemberFirstName.getText().equals("")){
                member.setFirstName(txtEditMemberFirstName.getText());
            }
            if(!txtEditMemberLastName.getText().equals("")){
                member.setLastName(txtEditMemberLastName.getText());
            }
            if(!dataPickerEditMember.getEditor().getText().equals("")){
                LocalDate dateOfBirth =  checkDate(dataPickerEditMember);
                member.setBirthDate(dateOfBirth);

            }
            tableViewMembers.refresh();
            scene.setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml", anchorPane);
        } catch (Exception ex) {
            lblEditMemberErrorMessage.setText(ex.getMessage());
        }
    }
    private LocalDate checkDate(DatePicker dateTimePicker) throws ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ld = LocalDate.parse(dateTimePicker.getEditor().getText(), formatter);

        return ld;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Members member = tableViewMembers.getSelectionModel().getSelectedItem();
        lblEditMember.setText("Edit Member: " + member.getFirstName());
        txtEditMemberFirstName.setPromptText(member.getFirstName());
        txtEditMemberLastName.setPromptText(member.getLastName());
        dataPickerEditMember.setPromptText(member.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}
