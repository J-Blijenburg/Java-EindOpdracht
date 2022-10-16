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

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public Label LblWelcome;

    public Members currentMember;
    @FXML
    public TableView tableViewMembers;

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
        tableViewMembers.setItems(listOfMembers);
    }
}
