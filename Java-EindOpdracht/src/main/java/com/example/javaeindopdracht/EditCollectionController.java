package com.example.javaeindopdracht;

import Model.Items;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditCollectionController implements Initializable {
    @FXML private AnchorPane anchorPane;
    @FXML  private ObservableList<Items> listOfItems;
    @FXML private TableView<Items> tableViewCollection;
    @FXML private Label lblItemsErrorMessage;
    @FXML private Label lblEditItems;
    @FXML private TextField txtEditItemTitle;
    @FXML private TextField txtEditItemsAuthor;

    public EditCollectionController(AnchorPane anchorPane, ObservableList<Items> listOfItems, TableView<Items> tableViewCollection) {
        this.anchorPane = anchorPane;
        this.listOfItems = listOfItems;
        this.tableViewCollection = tableViewCollection;
    }
    //While editing an item cancel the process and reset all the values
    @FXML public void btnCancelEditItems(ActionEvent event) throws IOException {
        setSceneOfCollection(new CollectionController(anchorPane, listOfItems));
    }

    //If information of the selected item is changed this button is going to confirm the changes
    @FXML public void btnEditItemConfirm(ActionEvent event) {
        try{
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                Items item = tableViewCollection.getSelectionModel().getSelectedItem();

                if(!txtEditItemTitle.getText().equals("")){
                    item.setTitle(txtEditItemTitle.getText());
                }
                if(!txtEditItemsAuthor.getText().equals("")){
                    item.setAuthor(txtEditItemsAuthor.getText());
                }
                tableViewCollection.refresh();
                setSceneOfCollection(new CollectionController(anchorPane, listOfItems));
            }
            else{
                throw new Exception("Please, Select an item");
            }

        }catch(Exception ex){
            lblItemsErrorMessage.setText(ex.getMessage());
        }
    }
    //set the scene with the given controller and fxml-file
    private void setSceneOfCollection(Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Collection-View.fxml"));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Items item = tableViewCollection.getSelectionModel().getSelectedItem();
        lblEditItems.setText("Edit item: " + item.getTitle());
        txtEditItemTitle.setPromptText(item.getTitle());
        txtEditItemsAuthor.setPromptText(item.getAuthor());
    }
}
