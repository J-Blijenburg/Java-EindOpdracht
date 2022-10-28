package com.example.javaeindopdracht;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditMemberController {
    @FXML
    private AnchorPane anchorPane;

    public EditMemberController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void btnCancelEditMember(ActionEvent event) throws IOException {
        setScene(new MembersController(anchorPane), "Members-View.fxml");
    }

    public void BtnEditMemberConfirm(ActionEvent event) {
    }

    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
