package com.example.javaeindopdracht;

import Model.Items;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CollectionController {

    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Items> listOfItems;

    public CollectionController(AnchorPane anchorPane, ObservableList<Items> listOfItems) {
        this.anchorPane = anchorPane;
        this.listOfItems = listOfItems;
    }

    @FXML public void btnAddItemOnAction(ActionEvent event) throws IOException {
        setScene(new AddCollectionController(anchorPane, listOfItems), "AddCollection-View.fxml");
    }

    @FXML public void btnEditItemsOnAction(ActionEvent event) throws IOException {
        setScene(new EditCollectionController(anchorPane, listOfItems), "EditCollection-View.fxml");
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
