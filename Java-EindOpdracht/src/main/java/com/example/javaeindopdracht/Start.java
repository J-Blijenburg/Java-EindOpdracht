package com.example.javaeindopdracht;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    public static Stage loginStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("Login-View.fxml"));
        Image image = new Image("..\\..\\..\\Images\\Icon.jpg");
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.getIcons().add(image);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        loginStage = stage;

    }

    public static void main(String[] args) {
        launch();
    }
}