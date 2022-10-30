package com.example.javaeindopdracht;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CreateScene {
    //set the scene with the given controller and fxml-file
    public void setScene(Object controller, String nameOfFxmlFile, AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }
}
