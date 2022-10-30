package com.example.javaeindopdracht;

import Database.Database;
import Model.Members;
import com.example.javaeindopdracht.Exception.DatabaseNotExistException;
import com.example.javaeindopdracht.Exception.EmptyLoginFieldsException;
import com.example.javaeindopdracht.Exception.PassWordException;
import com.example.javaeindopdracht.Exception.UserNameException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LoginController {
    @FXML public TextField userNametxt;
    @FXML public PasswordField passWordtxt;
    @FXML public Label lblErrorMessage;
    private Database database = new Database();
    private ObservableList<Members> listOfMembers;
    private File memberFile = new File("JavaEindopdrachtMembers.txt");
    private File itemFile = new File("JavaEindopdrachtItems.txt");

    public LoginController() throws IOException, ClassNotFoundException {
        this.listOfMembers = FXCollections.observableList(this.database.getAllMembers());
    }

    public void CheckDataBase(File file) throws DatabaseNotExistException {
        if(!file.exists()){
            throw new DatabaseNotExistException(file);
        }
    }

    //loop through every single member to identify the correct username and password
    public void loginBtnClick(ActionEvent event) {
       try{
           CheckDataBase(memberFile);
           CheckDataBase(itemFile);
           CheckUsernameAndPassword();
           for(Members member : listOfMembers){
               if (this.userNametxt.getText().equals(member.getFirstName())) {
                   if (!this.passWordtxt.getText().equals(member.getPassWord())) {
                       throw new PassWordException();
                   }
                   OpenNewStage(member, event);
                   break;
               }
           }
           throw new UserNameException();
       }
       catch(Exception ex){
           this.lblErrorMessage.setText("");
           this.lblErrorMessage.setText(ex.getMessage());
       }
    }
    //If there is no input remind the user to fill in a valid input
    private void CheckUsernameAndPassword() throws EmptyLoginFieldsException {
        if(this.userNametxt.getText().equals("") || this.passWordtxt.getText().equals("")){
            throw new EmptyLoginFieldsException();
        }
    }

    //Create a new stage with settings
    private void OpenNewStage(Members member, ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Dashboard-View.fxml"));
        DashboardController dashboardController = new DashboardController(member, database);
        fxmlLoader.setController(dashboardController);
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(new Scene(fxmlLoader.load()));
        secondaryStage.setResizable(false);
        secondaryStage.setTitle("DashBoard");
        secondaryStage.show();
        secondaryStage.setOnCloseRequest(windowEvent -> {

            try {
                database.Write();
            } catch (IOException e) {
                lblErrorMessage.setText("Could not write to database");
            }

        });
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }


}
