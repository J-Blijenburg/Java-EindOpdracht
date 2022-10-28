package com.example.javaeindopdracht;

import Model.Members;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LendingReceivingController implements Initializable {
    @FXML private Label LblWelcome;
    private Members currentMember;

    public LendingReceivingController(Members currentMember) {
        this.currentMember = currentMember;
    }

    @FXML public void BtnReceiveItemOnAction(ActionEvent event) {
    }


    @FXML public void BtnLendItemOnAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LblWelcome.setText("Welcome " + currentMember.getFirstName() + " " + currentMember.getLastName());
    }
}
