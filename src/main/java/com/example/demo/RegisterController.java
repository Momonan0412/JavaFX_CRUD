package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    public Button btn_AccountExistProceedToLoginScene;
    @FXML
    public Button btn_Register;
    @FXML
    public CheckBox cBox_Java;
    @FXML
    public CheckBox cBox_CPlusPlus;
    @FXML
    public CheckBox cBox_C;
    @FXML
    public TextField txtFld_StudentID;
    @FXML
    public TextField txtFld_Email;
    @FXML
    public RadioButton rBtn_Others;
    @FXML
    public RadioButton rBtn_Male;
    @FXML
    public RadioButton rBtn_Female;
    @FXML
    public TextField txtFld_LastName;
    @FXML
    public TextField txtFld_FirstName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}