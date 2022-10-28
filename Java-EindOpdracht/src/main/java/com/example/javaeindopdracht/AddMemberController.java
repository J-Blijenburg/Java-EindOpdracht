package com.example.javaeindopdracht;

import Model.Members;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddMemberController  {

    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Members> listOfMembers;

    public AddMemberController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    @FXML public void btnCancelNewMember(ActionEvent event) throws IOException {
        setScene(new MembersController(anchorPane, listOfMembers), "Members-View.fxml");
    }

    @FXML public void btnAddMemberConfirm(ActionEvent event) {
    }

    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
