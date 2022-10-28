package com.example.javaeindopdracht;

import Model.Items;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddCollectionController {

    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Items> listOfItems;

    public AddCollectionController(AnchorPane anchorPane, ObservableList<Items> listOfItems) {
        this.anchorPane = anchorPane;
        this.listOfItems = listOfItems;
    }

    @FXML public void BtnCancelNewItem(ActionEvent event) throws IOException {
        setScene(new CollectionController(anchorPane, listOfItems), "Collection-View.fxml");
    }

    @FXML public void BtnAddItemConfirm(ActionEvent event) {
    }

    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
