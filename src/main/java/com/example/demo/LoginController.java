package com.example.demo;

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

    }
}
