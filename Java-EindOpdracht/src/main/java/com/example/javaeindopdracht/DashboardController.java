package com.example.javaeindopdracht;

import Database.Database;
import Model.Items;
import Model.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private ImageView DashBoardImage;
    @FXML
    private Label LblWelcome;

    private Members currentMember;
    @FXML
    private TableView tableViewMembers;

    @FXML
    private TableView tableViewCollection;

    private ObservableList<Members> listOfMembers;
    private ObservableList<Items> listOfItems;

    private Database db = new Database();

    public DashboardController(Members member){
       this.currentMember = member;
        this.listOfMembers = FXCollections.observableList(this.db.getAllMembers());
        this.listOfItems = FXCollections.observableList(this.db.getAllItems());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LblWelcome.setText("Welcome " + currentMember.getFirstName());
        tableViewMembers.setItems(listOfMembers);
        tableViewCollection.setItems(listOfItems);
        File file = new File("/Images/LibrarySystem.jpg");
        Image image = new Image(file.toURI().toString());
        DashBoardImage.setImage(image);
    }
}
