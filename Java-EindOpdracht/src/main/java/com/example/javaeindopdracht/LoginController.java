package com.example.javaeindopdracht;

import Database.Database;
import Model.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Iterator;

public class LoginController {
    private Database db = new Database();
    private ObservableList<Members> listOfMembers;
    public LoginController() {
        this.listOfMembers = FXCollections.observableList(this.db.getAllMembers());
    }

    public void loginBtnClick(ActionEvent actionEvent) {
        try {
            if (!(!this.userNametxt.getText().equals("") | !this.passWordtxt.getText().equals(""))) {
                throw new Exception("Please, Enter a Username and Password.");
            } else {
                Iterator var2 = this.members.iterator();

                while(var2.hasNext()) {
                    Members member = (Members)var2.next();
                    if (this.userNametxt.getText().equals(member.getFirstName())) {
                        if (!this.passWordtxt.getText().equals(member.getPassWord())) {
                            throw new Exception("Invalid Password combination");
                        }

                        this.currentMember = member;
                        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("DashBoard-view.fxml"));
                        Parent root = (Parent)fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setTitle("DashBoard");
                        stage.setScene(new Scene(root));
                        stage.show();
                        Start.loginStage.close();
                        break;
                    }
                }

                throw new Exception("Invalid Username combination");
            }
        } catch (Exception var7) {
            this.LblErrorMessage.setText("");
            this.LblErrorMessage.setText(var7.getMessage());
        }
    }
}
