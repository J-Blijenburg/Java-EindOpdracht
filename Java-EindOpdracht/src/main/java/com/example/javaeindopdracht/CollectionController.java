package com.example.javaeindopdracht;

import Model.Items;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {
    @FXML private TableColumn<Items, String> tableViewItemsLendOutBy;
    @FXML private TableColumn<Items, String > tableViewItemsAvailable;

    @FXML private ObservableList<Items> listOfItems;
    @FXML private TableView<Items> tableViewCollection;

    @FXML private Label lblItemsErrorMessage;
    @FXML private AnchorPane anchorPane;
    @FXML private TextField txtSearchItem;
    public CollectionController(AnchorPane anchorPane, ObservableList<Items> listOfItems) {
        this.anchorPane = anchorPane;
        this.listOfItems = listOfItems;
    }

    //display the view to create/add a new item
    @FXML public void btnAddItemOnAction(ActionEvent event) throws IOException {
        setScene(new AddCollectionController(anchorPane, listOfItems, tableViewCollection), "AddCollection-View.fxml");
    }

    @FXML public void btnEditItemsOnAction(ActionEvent event) {
        try{
            //Check if an item is selected and bring that item to the edit-page.
            if(tableViewCollection.getSelectionModel().getSelectedItem() != null){
                setScene(new EditCollectionController(anchorPane, listOfItems, tableViewCollection), "EditCollection-View.fxml");
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
                tableViewCollection.setItems(listOfItems);
                txtSearchItem.setText("");
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
        changeTableViewAvailable();
        changeTableViewLendOutBy();
        txtSearchItem.textProperty().addListener((obs, oldText, newText) -> searchItem());
    }

    //Receives the data what in the cell is displayed and change it to the right string
    private void changeTableViewAvailable(){
        tableViewItemsAvailable.setCellValueFactory(cellData -> {
            Items item = cellData.getValue();
            if(item.getAvailable()){
                return new SimpleStringProperty("Yes");
            }
            return  new SimpleStringProperty("No");
        });
    }
    //Receives the data what in the cell is displayed and change it to the right string
    private void changeTableViewLendOutBy(){
        tableViewItemsLendOutBy.setCellValueFactory(cellData -> {
            Items item = cellData.getValue();
            if(item.getLendOutBy() != null){
                return new SimpleStringProperty(cellData.getValue().getLendOutBy().getFirstName());
            }
            return new SimpleStringProperty(null);
        });
    }

    //search function for items
    private void searchItem(){
        String searchItem = txtSearchItem.getText().toLowerCase();
        if(txtSearchItem.getText().equals("")){
            tableViewCollection.setItems(listOfItems);
        }
        else{
            ObservableList<Items> filter = FXCollections.observableArrayList();
            for(Items item : listOfItems){
                if(item.getTitle().toLowerCase().contains(searchItem) | item.getAuthor().toLowerCase().contains(searchItem)){
                    filter.add(item);
                }
            }
            tableViewCollection.setItems(filter);
        }
    }
}
