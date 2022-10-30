package com.example.javaeindopdracht;

import Database.Database;
import Model.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class LoginController {
    @FXML public TextField userNametxt;
    @FXML public PasswordField passWordtxt;
    @FXML public Label LblErrorMessage;
    private Database database = new Database();
    private ObservableList<Members> listOfMembers;
    private File memberFile = new File("JavaEindopdrachtMembers.txt");
    private File itemFile = new File("JavaEindopdrachtItems.txt");

    public LoginController() throws IOException, ClassNotFoundException {
        this.listOfMembers = FXCollections.observableList(this.database.getAllMembers());
    }

    public void CheckDataBase(File file) throws Exception {
        if(!file.exists()){
            throw new Exception("Database file " + file.getName() + " does not exist!");
        }
    }

    //loop through every single member to identify the correct username and password
    public void loginBtnClick(ActionEvent actionEvent) {
       try{
           CheckDataBase(memberFile);
           CheckDataBase(itemFile);
           CheckUsernameAndPassword();
           for(Members member : listOfMembers){
               if (this.userNametxt.getText().equals(member.getFirstName())) {
                   if (!this.passWordtxt.getText().equals(member.getPassWord())) {
                       throw new Exception("Invalid Password combination");
                   }
                   OpenNewStage(member);
                   break;
               }
           }
           throw new Exception("Username does not exist!");
       }
       catch(Exception ex){
           this.LblErrorMessage.setText("");
           this.LblErrorMessage.setText(ex.getMessage());
       }
    }
    //If there is no input remind the user to fill in a valid input
    private void CheckUsernameAndPassword() throws Exception {
        if(this.userNametxt.getText().equals("") | this.passWordtxt.getText().equals("")){
            throw new Exception("Please, Enter a Username and Password.");
        }
    }

    //Create a new stage with settings
    private void OpenNewStage(Members member) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Dashboard-View.fxml"));
        DashboardController dashboardController = new DashboardController(member, database);
        fxmlLoader.setController(dashboardController);
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setResizable(false);
        stage.setTitle("DashBoard");
        stage.show();
        Start.loginStage.close();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                try {
                    database.Write();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
