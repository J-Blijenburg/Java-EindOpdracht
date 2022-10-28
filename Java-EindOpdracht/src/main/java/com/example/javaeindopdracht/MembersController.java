package com.example.javaeindopdracht;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MembersController {

    @FXML private AnchorPane anchorPane;
    @FXML private Label lblMembersErrorMessage;

    public MembersController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void BtnDeleteMemberOnAction(ActionEvent event) {
    }

    public void BtnEditMemberOnAction(ActionEvent event) {
    }

    public void btnAddMemberOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMember-View.fxml"));
        loader.setController(new AddMemberController(anchorPane));
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
