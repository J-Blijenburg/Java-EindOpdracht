package com.example.javaeindopdracht;

import Model.Members;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml");
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
            setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml");
        } catch (Exception ex) {
            lblEditMemberErrorMessage.setText(ex.getMessage());
        }
    }
    private LocalDate checkDate(DatePicker dateTimePicker) throws ParseException {
        //https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
        DateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
        Date date = (Date) sd.parse(dateTimePicker.getEditor().getText());
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDate ld = zonedDateTime.toLocalDate();

        return ld;
    }




    public void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
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
