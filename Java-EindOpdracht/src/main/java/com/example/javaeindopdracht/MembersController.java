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

    @FXML public void BtnDeleteMemberOnAction(ActionEvent event) {
    }

    @FXML public void BtnEditMemberOnAction(ActionEvent event) throws IOException {
        setScene(new EditMemberController(anchorPane), "EditMember-View.fxml");
    }

    @FXML public void btnAddMemberOnAction(ActionEvent event) throws IOException {
        setScene(new AddMemberController(anchorPane), "AddMember-View.fxml");
    }

    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        //https://stackoverflow.com/questions/53127331/javafx-swap-anchorpane-element-with-fxml-from-another-file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
