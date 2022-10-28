package com.example.javaeindopdracht;

import Model.Members;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditMemberController {
    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Members> listOfMembers;
    @FXML private TableView<Members> tableViewMembers;
    @FXML private TextField txtEditMemberFirstName;
    @FXML private TextField txtEditMemberLastName;
    @FXML private DatePicker dataPickerEditMember;

    public EditMemberController(AnchorPane anchorPane, ObservableList<Members> listOfMembers, TableView<Members> tableViewMembers) {
        this.anchorPane = anchorPane;
        this.listOfMembers = listOfMembers;
        this.tableViewMembers = tableViewMembers;
    }

    @FXML public void btnCancelEditMember(ActionEvent event) throws IOException {
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
            if(!dataPickerEditMember.getId().equals("")){
                member.setBirthDate(dataPickerEditMember.getValue());
            }

            tableViewMembers.refresh();
            setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
