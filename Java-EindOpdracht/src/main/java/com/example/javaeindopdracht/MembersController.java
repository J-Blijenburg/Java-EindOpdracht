package com.example.javaeindopdracht;

import Model.Members;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MembersController implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private Label lblMembersErrorMessage;
    @FXML private ObservableList<Members> listOfMembers;
    @FXML private TableView<Members> tableViewMembers;

    @FXML private Label lblEditMember;


    public MembersController(AnchorPane anchorPane, ObservableList<Members> listOfMembers) {
        this.anchorPane = anchorPane;
        this.listOfMembers = listOfMembers;
    }
    //Check if a member is selected. If so delete the member
    @FXML public void btnDeleteMemberOnAction(ActionEvent event) {
        try{
            if(tableViewMembers.getSelectionModel().getSelectedItem() != null){
                listOfMembers.remove(tableViewMembers.getSelectionModel().getSelectedItem());
                tableViewMembers.refresh();
            }
            else{
                throw new Exception("Please, Select an member");
            }
        }catch(Exception ex){
            lblMembersErrorMessage.setText(ex.getMessage());
        }
    }
    //Check if a member is selected and bring that member to the edit-page.
    @FXML public void btnEditMemberOnAction(ActionEvent event) throws IOException {
        try{
            if(tableViewMembers.getSelectionModel().getSelectedItem() != null){
                Members member = tableViewMembers.getSelectionModel().getSelectedItem();
                lblEditMember.setText("Edit Member: " + member.getFirstName());
                //txtEditMemberFirstName.setPromptText(member.getFirstName());
                //txtEditMemberLastName.setPromptText(member.getLastName());
                //dataPickerEditMember.setPromptText(member.getBirthDate().toString());
                setScene(new EditMemberController(anchorPane, listOfMembers, tableViewMembers), "EditMember-View.fxml");
            }
            else{
                throw new Exception("Please, Select an member");
            }
        }catch(Exception ex){
            lblMembersErrorMessage.setText(ex.getMessage());
        }

    }
    //it will show the view on how to add a new member
    @FXML public void btnAddMemberOnAction(ActionEvent event) throws IOException {
        setScene(new AddMemberController(anchorPane, tableViewMembers), "AddMember-View.fxml");
    }

    private void setScene(Object controller, String nameOfFxmlFile) throws IOException {
        //https://stackoverflow.com/questions/53127331/javafx-swap-anchorpane-element-with-fxml-from-another-file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFxmlFile));
        loader.setController(controller);
        AnchorPane an =  loader.load();
        anchorPane.getChildren().setAll(an);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewMembers.setItems(listOfMembers);
    }
}
