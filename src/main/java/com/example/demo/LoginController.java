package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public TextField txtFld_UserName;
    @FXML
    public PasswordField txtFld_Password;
    @FXML
    public Button btn_Login;
    @FXML
    public Button btn_CreateAccount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_Login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(DatabaseUtilities.checkIfDataExistInTable(txtFld_UserName.getText(),txtFld_Password.getText())){
                    System.out.println("Success!");
                    SceneUtilities.changeScene(actionEvent,"Display.fxml", "");
                }
            }
        });
    }
}
