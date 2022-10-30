package com.example.javaeindopdracht;

import Model.Items;
import com.example.javaeindopdracht.Exception.EmptyFieldsException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddCollectionController {
    @FXML private TextField txtAddItemsAuthor;
    @FXML private AnchorPane anchorPane;
    @FXML private ObservableList<Items> listOfItems;
    @FXML private TableView<Items> tableViewCollection;
    @FXML private TextField txtAddItemsTitle;
    @FXML private Label lblEditItemsErrorMessage;
    private Scene scene = new Scene();

    public AddCollectionController(AnchorPane anchorPane, ObservableList<Items> listOfItems, TableView<Items> tableViewCollection) {
        this.anchorPane = anchorPane;
        this.listOfItems = listOfItems;
        this.tableViewCollection = tableViewCollection;
    }
    //While creating a new item cancel the process and reset all the values
    @FXML public void btnCancelNewItem(ActionEvent event) throws IOException {
        txtAddItemsAuthor.setText("");
        txtAddItemsTitle.setText("");
        scene.setScene(new CollectionController(anchorPane, listOfItems), "Collection-View.fxml", anchorPane);
    }

    //When entering all the needed information you can add the new item
    @FXML public void btnAddItemConfirm(ActionEvent event) {
        try{
            if(txtAddItemsTitle.getText() != null || txtAddItemsAuthor.getText() != null){
                listOfItems.add(new Items(listOfItems.size() + 1, true, txtAddItemsTitle.getText(), txtAddItemsAuthor.getText()));
                scene.setScene(new CollectionController(anchorPane, listOfItems), "Collection-View.fxml", anchorPane);
            }
            else{
                throw new EmptyFieldsException();
            }
        }catch(Exception ex){
            lblEditItemsErrorMessage.setText(ex.getMessage());
        }
    }




}
