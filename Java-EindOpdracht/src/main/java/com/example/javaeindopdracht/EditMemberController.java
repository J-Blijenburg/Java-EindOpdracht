package com.example.javaeindopdracht;

import Model.Members;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditMemberController {
    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Members> listOfMembers;

    public EditMemberController(AnchorPane anchorPane, ObservableList<Members> listOfMembers) {
        this.anchorPane = anchorPane;
        this.listOfMembers = listOfMembers;
    }

    @FXML public void btnCancelEditMember(ActionEvent event) throws IOException {
        setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml");
    }

    @FXML public void BtnEditMemberConfirm(ActionEvent event) {
    }

    public void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
