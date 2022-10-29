package com.example.javaeindopdracht;

import Database.Database;
import Model.Items;
import Model.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private Label lblMembersErrorMessage;
    @FXML private VBox vboxMembers;
    @FXML private VBox vboxAddNewMembers;
    @FXML private VBox vboxEditMembers;
    @FXML private VBox vboxCollection;
    @FXML private VBox vboxAddNewItem;
    @FXML private VBox vboxEditItems;
    @FXML private TabPane tabPane;
    @FXML private Tab tabLendingReceiving;
    @FXML private AnchorPane dashBoardAnchorPane;
    private final Database database;
    private final Members currentMember;
    private ObservableList<Members> listOfMembers;
    private ObservableList<Items> listOfItems;
    //Receive the current member and database from the Login-controller
    public DashboardController(Members member, Database database){
        this.currentMember = member;
        this.database = database;
    }
    //Greet the current member and load all the list in the correct tableView. It also makes sure that the right Tap is opened
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.listOfMembers = FXCollections.observableList(database.getAllMembers());
            this.listOfItems = FXCollections.observableList(database.getAllItems());
            setScene(new LendingReceivingController(listOfItems, listOfMembers, currentMember), "LendingReceiving-View.fxml");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void membersOnAction() throws IOException{
        setScene(new MembersController(dashBoardAnchorPane, listOfMembers),"Members-View.fxml");
    }
    @FXML private void collectionOnAction() throws IOException{
        setScene(new CollectionController(dashBoardAnchorPane, listOfItems), "Collection-View.fxml");
    }
    @FXML private void lendingReceivingOnAction() throws IOException {
        setScene(new LendingReceivingController(listOfItems, listOfMembers, currentMember), "LendingReceiving-View.fxml");
    }

    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        //https://stackoverflow.com/questions/53127331/javafx-swap-anchorpane-element-with-fxml-from-another-file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        dashBoardAnchorPane.getChildren().setAll(an);
    }
}
