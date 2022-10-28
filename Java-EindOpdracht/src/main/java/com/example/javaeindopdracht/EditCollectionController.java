package com.example.javaeindopdracht;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditCollectionController {
    @FXML
    private AnchorPane anchorPane;

    public EditCollectionController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void BtnCancelEditItems(ActionEvent event) throws IOException {
        setScene(new CollectionController(anchorPane), "Collection-View.fxml");
    }

    public void BtnEditItemConfirm(ActionEvent event) {
    }
    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
