package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayController implements Initializable {
    @FXML
    public Text txt_FirstName;
    @FXML
    public Text txt_LastName;
    @FXML
    public Text txt_UserName;
    @FXML
    public Text txt_Email;
    @FXML
    public Text txt_StudentNumber;
    @FXML
    public Text txt_Gender;
    @FXML
    public Text txt_FavoriteProgLanguage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
