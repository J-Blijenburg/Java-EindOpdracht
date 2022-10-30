package com.example.javaeindopdracht;

import Model.Items;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    public AddCollectionController(AnchorPane anchorPane, ObservableList<Items> listOfItems, TableView<Items> tableViewCollection) {
        this.anchorPane = anchorPane;
        this.listOfItems = listOfItems;
        this.tableViewCollection = tableViewCollection;
    }
    //While creating a new item cancel the process and reset all the values
    @FXML public void btnCancelNewItem(ActionEvent event) throws IOException {
        txtAddItemsAuthor.setText("");
        txtAddItemsTitle.setText("");
        setScene(new CollectionController(anchorPane, listOfItems), "Collection-View.fxml");
    }

    //When entering all the needed information you can add the new item
    @FXML public void btnAddItemConfirm(ActionEvent event) {
        try{
            if(txtAddItemsTitle.getText() != null || txtAddItemsAuthor.getText() != null){
                listOfItems.add(new Items(listOfItems.size() + 1, true, txtAddItemsTitle.getText(), txtAddItemsAuthor.getText()));
                setScene(new CollectionController(anchorPane, listOfItems), "Collection-View.fxml");
            }
            else{
                throw new Exception("Please, fill in all the fields");
            }
        }catch(Exception ex){
            lblEditItemsErrorMessage.setText(ex.getMessage());
        }
    }

    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }


}
