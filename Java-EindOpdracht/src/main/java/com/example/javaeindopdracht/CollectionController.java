package com.example.javaeindopdracht;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CollectionController {

    @FXML private AnchorPane anchorPane;

    public CollectionController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    @FXML public void btnAddItemOnAction(ActionEvent event) throws IOException {
        setScene(new AddCollectionController(anchorPane), "AddCollection-View.fxml");
    }

    @FXML public void btnEditItemsOnAction(ActionEvent event) throws IOException {
        setScene(new EditCollectionController(anchorPane), "EditCollection-View.fxml");
    }

    @FXML public void btnDeleteItemOnAction(ActionEvent event) {
    }

    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
