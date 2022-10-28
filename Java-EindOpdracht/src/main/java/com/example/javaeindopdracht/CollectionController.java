package com.example.javaeindopdracht;

import Model.Items;
import Model.Members;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {
    @FXML private TableColumn<Items, String> tableViewItemsLendOutBy;
    @FXML private TableColumn<Items, String > tableViewItemsAvailable;

    @FXML private ObservableList<Items> listOfItems;
    @FXML private TableView<Items> tableViewCollection;
    @FXML private Label lblEditItems;
    @FXML private Label lblItemsErrorMessage;
    @FXML private AnchorPane anchorPane;
    public CollectionController(AnchorPane anchorPane, ObservableList<Items> listOfItems) {
        this.anchorPane = anchorPane;
        this.listOfItems = listOfItems;
    }

    @FXML public void btnAddItemOnAction(ActionEvent event) throws IOException {
        setScene(new AddCollectionController(anchorPane, listOfItems, tableViewCollection), "AddCollection-View.fxml");
    }

    @FXML public void btnEditItemsOnAction(ActionEvent event) throws IOException {
        try{
            //Check if an item is selected and bring that item to the edit-page.
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                setScene(new EditCollectionController(anchorPane, listOfItems, tableViewCollection), "EditCollection-View.fxml");
                Items item = tableViewCollection.getSelectionModel().getSelectedItem();
                lblEditItems.setText("Edit item: " + item.getTitle());
                //txtEditItemTitle.setPromptText(item.getTitle());
                //txtEditItemsAuthor.setPromptText(item.getAuthor());
            }
            else{
                throw new Exception("Please, Select an item");
            }
        }catch(Exception ex){
            lblItemsErrorMessage.setText(ex.getMessage());
        }
    }
    //Check if an item is selected. If so delete the item
    @FXML public void btnDeleteItemOnAction(ActionEvent event) {
        try{
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                listOfItems.remove(tableViewCollection.getSelectionModel().getSelectedItem());
                tableViewCollection.refresh();
            }
            else{
                throw new Exception("Please, Select an item");
            }
        }catch (Exception ex){
            lblItemsErrorMessage.setText(ex.getMessage());
        }
    }

    //set the scene with the given controller and fxml-file
    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewCollection.setItems(listOfItems);
        ChangeTableViewAvailable();
        ChangeTableViewLendOutBy();

    }

    //Receives the data what in the cell is displayed and change it to the right string
    private void ChangeTableViewAvailable(){
        tableViewItemsAvailable.setCellValueFactory(cellData -> {
            Items item = cellData.getValue();
            if(item.getAvailable()){
                return new SimpleStringProperty("Yes");
            }

            return  new SimpleStringProperty("No");
        });
    }
    //Receives the data what in the cell is displayed and change it to the right string
    private void ChangeTableViewLendOutBy(){
        tableViewItemsLendOutBy.setCellValueFactory(cellData -> {
            Items item = cellData.getValue();
            if(item.getLendOutBy() != null){
                return new SimpleStringProperty(cellData.getValue().getLendOutBy().getFirstName());
            }
            return new SimpleStringProperty(null);
        });
    }

}
